package view.story

import controller.game.subcontroller.StoryController
import model.nodes.Pathway
import view.AbstractView
import view.story.panels.{NarrativePanel, PathwaysPanel}
import view.util.common.ControlsPanel
import view.util.scalaQuestSwingComponents.dialog.{SqSwingDialog, SqYesNoSwingDialog}
import view.util.scalaQuestSwingComponents.SqSwingButton

import java.awt.BorderLayout
import java.awt.event.ActionEvent

/**
 * Represents the GUI for the navigation between [[model.nodes.StoryNode]].
 */
sealed trait StoryView extends AbstractView {

  /**
   * Shows the user new events by creating SqSwingDialogs.
   *
   * @param eventsList a list containing every new event name.
   */
  def displayEvent(eventsList: List[String]): Unit

  /**
   * Allows the narrative to be rendered.
   *
   * @param narrative the narrative to display.
   */
  def setNarrative(narrative: String): Unit

  /**
   * Allows rendering of all of the possible pathways.
   *
   * @param pathways the pathways to render.
   */
  def setPathways(pathways: Set[Pathway]): Unit
}

object StoryView {

  private class StoryViewSwing(private val storyController: StoryController) extends StoryView {
    private var _narrative: String = ""
    private var _pathways: Seq[Pathway] = Seq()
    var eventsNameList: List[String] = List()

    this.setLayout(new BorderLayout())

    override def setNarrative(narrative: String): Unit = _narrative = narrative

    override def setPathways(pathways: Set[Pathway]): Unit = _pathways = pathways.toSeq

    override def displayEvent(eventsList: List[String]): Unit = {
      eventsList.foreach(e => {
        SqSwingDialog("New Event!", e, List(
          SqSwingButton("ok", _ => {})), closable = false)
      })
    }

    override def populateView(): Unit = {
      this.add(
        ControlsPanel(
          List(
            ("s", ("[S] Status", _ => storyController.goToStatStatus())),
            ("h", ("[H] History", _ => storyController.goToHistory())),
            ("p", ("[P] Save Progress", _ => storyController.goToProgressSaver())),
            ("i", ("[I] Inventory", _ => storyController.goToInventory())),
            ("q", ("[Q] Quit", _ =>
              SqYesNoSwingDialog(
                "Exit Confirm",
                "Do you really want to exit the game?",
                (_: ActionEvent) => storyController.close()))
            ))
        ),BorderLayout.NORTH
      )
      this.add(NarrativePanel(_narrative), BorderLayout.CENTER)
      this.add(PathwaysPanel(_pathways, p => storyController.choosePathWay(p)), BorderLayout.SOUTH)
    }
  }

  def apply(storyController: StoryController): StoryView = new StoryViewSwing(storyController)

}
