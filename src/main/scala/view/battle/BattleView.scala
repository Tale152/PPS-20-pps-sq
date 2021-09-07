package view.battle

import controller.game.subcontroller.BattleController
import view.AbstractView
import view.story.NarrativePanel
import view.util.common.ControlsPanel
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.SqSwingDialog.SqSwingDialog
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog
import java.awt.BorderLayout
import java.awt.event.ActionEvent

import view.battle.panels.OpponentsPanel

/**
 * It is a GUI that handles the Battle. Associated with [[controller.game.subcontroller.BattleController]].
 */
trait BattleView extends AbstractView {
  /**
   * Used to render the narrative of the story.
   * @param narrative the string that is going to be printed.
   */
  def narrative(narrative: String): Unit

  /**
   * Used to render a dialog based on the result of the escape attempt.
   * @param escaped if the attempt has been successful or not.
   */
  def escapeResult(escaped: Boolean = true): Unit

  /**
   * Used to render a dialog based on the result of the battle.
   * @param victory if the battle has been won or not.
   */
  def battleResult(victory: Boolean = true): Unit

  /**
   * Sets the player name.
   * @param name the player name.
   */
  def setPlayerName(name: String): Unit

  /**
   * Sets the player health.
   * @param health the tuple of the player current health and the max health.
   */
  def setPlayerHealth(health: (Int, Int)): Unit

  /**
   * Sets the enemy name.
   * @param name the enemy name.
   */
  def setEnemyName(name: String): Unit

  /**
   * Sets the enemy health.
   * @param health the tuple of the player current health and the max health.
   */
  def setEnemyHealth(health: (Int, Int)): Unit
}

object BattleView {

  def apply(battleController: BattleController): BattleView = new BattleViewSwing(battleController)

  private class BattleViewSwing(private val battleController: BattleController) extends BattleView {
    private var _narrative: String = ""
    private var _playerName: String = ""
    private var _playerHealth: (Int, Int) = (0, 0)
    private var _enemyName: String = ""
    private var _enemyHealth: (Int, Int) = (0, 0)

    this.setLayout(new BorderLayout())

    override def narrative(narrative: String): Unit = _narrative = narrative

    override def setPlayerName(name: String): Unit = _playerName = name

    override def setPlayerHealth(health: (Int, Int)): Unit = _playerHealth = health

    override def setEnemyName(name: String): Unit = _enemyName = name

    override def setEnemyHealth(health: (Int, Int)): Unit = _enemyHealth = health

    override def populateView(): Unit = {
      this.add(OpponentsPanel(_playerName, _playerHealth, _enemyName, _enemyHealth), BorderLayout.NORTH)
      this.add(NarrativePanel(_narrative), BorderLayout.CENTER)
      this.add(
        ControlsPanel(
          List(
            ("a", ("[A] Attack", _ => battleController.attack())),
            ("e", ("[E] Escape", _ => battleController.attemptEscape())),
            ("i", ("[I] Inventory", _ => battleController.goToInventory())),
            ("q", ("[Q] Quit", _ =>
              SqYesNoSwingDialog("Exit Confirm", "Do you really want to exit the game?",
                (_: ActionEvent) => battleController.close(), (_: ActionEvent) => battleController.close())))
          )),
        BorderLayout.SOUTH
      )
    }

    override def battleResult(victory: Boolean): Unit = {
      val resultText: String = if (victory) "won" else "lost"
      SqSwingDialog("Battle " + resultText + "!" ,
        "You " + resultText + " the battle",
        List(SqSwingButton("ok", _ => if (victory) battleController.goToStory() else battleController.close())),
        closable = false)
    }

    override def escapeResult(escaped: Boolean): Unit = {
      val resultText: String = "Escape" + (if (escaped) "d successfully" else " failed") + "!"
      val resultDescription: String = "You" +
        (if (escaped) " escaped successfully from the battle" else "r attempt to escape failed")
      val resultStrategy: Unit = if (escaped) battleController.goToStory() else battleController.escapeFailed()

      SqSwingDialog(resultText ,
        resultDescription,
        List(SqSwingButton("ok", _ => resultStrategy)),
        closable = false)
    }
  }
}
