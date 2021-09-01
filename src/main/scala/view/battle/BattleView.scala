package view.battle

import controller.game.subcontroller.BattleController
import view.AbstractView
import view.story.NarrativePanel
import view.util.common.ControlsPanel
import view.util.scalaQuestSwingComponents.SqSwingButton.SqSwingButton
import view.util.scalaQuestSwingComponents.SqSwingDialog.SqSwingDialog

import java.awt.BorderLayout
import java.awt.event.ActionEvent

trait BattleView extends AbstractView {
  def narrative(narrative: String): Unit
  def playerTurn(): Unit
}

object BattleView {
  def apply(battleController: BattleController): BattleView = new BattleViewSwing(battleController)

  private class BattleViewSwing(private val battleController: BattleController) extends BattleView {
    private var _narrative: String = ""

    this.setLayout(new BorderLayout())

    override def narrative(narrative: String): Unit = _narrative = narrative

    //enable actions buttons
    override def playerTurn(): Unit = ???

    /**
     * Sub-portion of render() where graphical elements are added
     */
    override def populateView(): Unit = {
      this.add(
        ControlsPanel(
          List(
            ("i", ("[I] Inventory", _ => battleController.goToInventory())),
            ("q", ("[Q] Quit", _ => {
              SqSwingDialog("Exit Confirm", "Do you really want to exit the game?",
                List(new SqSwingButton("yes", (_: ActionEvent) => battleController.close(), true),
                  new SqSwingButton("no", (_: ActionEvent) => {}, true)))
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
  }
}