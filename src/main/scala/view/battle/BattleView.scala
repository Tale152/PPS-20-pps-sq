package view.battle

import controller.game.subcontroller.BattleController
import view.AbstractView
import view.story.NarrativePanel
import view.util.characterInfo.CharacterNamePanel
import view.util.common.ControlsPanel
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.SqSwingDialog.SqSwingDialog
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog

import java.awt.BorderLayout
import java.awt.event.ActionEvent

trait BattleView extends AbstractView {
  def narrative(narrative: String): Unit
  def characterTurn(): Unit
  def escapeResult(escaped: Boolean = true): Unit
  def battleResult(victory: Boolean = true): Unit
  def setPlayerName(name: String)
  def setPlayerHealth(health: (Int, Int))
  def setEnemyName(name: String)
  def setEnemyHealth(health: (Int, Int))
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

    //enable actions buttons (probably it will have a character as parameter to enable and disable buttons)
    override def characterTurn(): Unit = println("new turn")

    /**
     * Sub-portion of render() where graphical elements are added
     */
    override def populateView(): Unit = {
      this.add(
        ControlsPanel(
          List(
            ("i", ("[I] Inventory", _ => battleController.goToInventory())),
            ("q", ("[Q] Quit", _ =>
              SqYesNoSwingDialog("Exit Confirm", "Do you really want to exit the game?",
                (_: ActionEvent) => battleController.close(), (_: ActionEvent) => battleController.close())))
          )),
            BorderLayout.NORTH
          )

      //TODO populate a new component
      this.add(CharacterNamePanel(_playerName), BorderLayout.EAST)
      this.add(CharacterNamePanel(_enemyName), BorderLayout.WEST)
      this.add(NarrativePanel(_narrative), BorderLayout.CENTER)

      this.add(
        ControlsPanel(
          List(
            ("a", ("[A] Attack", _ => battleController.attack())),
            ("e", ("[E] Escape", _ => battleController.attemptEscape())),
          )),
        BorderLayout.SOUTH
      )
    }

    override def battleResult(victory: Boolean): Unit = {
      val resultText: String = if (victory) "won" else "lost"
      SqSwingDialog("Battle " + resultText + "!" ,
        "You " + resultText + " the battle",
        List(SqSwingButton("ok", (_: ActionEvent) => battleController.goToStory())),
        closable = false)
    }

    override def escapeResult(escaped: Boolean): Unit = {
      val resultText: String = "Escape" + (if (escaped) "d successfully" else " failed") + "!"
      val resultDescription: String = "You" +
        (if (escaped) " escaped successfully from the battle" else "r attempt to escape failed")
      val resultStrategy: Unit = if (escaped) battleController.goToStory() else battleController.enemyTurn()

      SqSwingDialog(resultText ,
        resultDescription,
        List(SqSwingButton("ok", (_: ActionEvent) => resultStrategy)),
        closable = false)
    }

    override def setPlayerName(name: String): Unit = _playerName = name

    override def setPlayerHealth(health: (Int, Int)): Unit = _playerHealth = health

    override def setEnemyName(name: String): Unit = _enemyName = name

    override def setEnemyHealth(health: (Int, Int)): Unit = _enemyHealth = health
  }
}
