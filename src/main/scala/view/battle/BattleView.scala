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

trait BattleView extends AbstractView {
  def narrative(narrative: String): Unit
  def characterTurn(): Unit
  def escapeResult(escaped: Boolean = true): Unit
  def battleResult(victory: Boolean = true): Unit
}

object BattleView {
  def apply(battleController: BattleController): BattleView = new BattleViewSwing(battleController)

  private class BattleViewSwing(private val battleController: BattleController) extends BattleView {
    private var _narrative: String = ""

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
            ("q", ("[Q] Quit", _ => {
              SqYesNoSwingDialog("Exit Confirm", "Do you really want to exit the game?",
                (_: ActionEvent) => battleController.close(), (_: ActionEvent) => {})
            })))),
            BorderLayout.NORTH
          )

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
  }
}
