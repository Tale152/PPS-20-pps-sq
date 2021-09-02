package view.story

import controller.game.subcontroller.StoryController
import model.nodes.Pathway
import view.AbstractView
import view.util.common.ControlsPanel
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.SqSwingButton.SqSwingButton
import view.util.scalaQuestSwingComponents.SqSwingDialog.SqSwingDialog

import java.awt.BorderLayout
import java.awt.event.ActionEvent

/**
 * Represents the GUI for the navigation between [[model.nodes.StoryNode]]
 */
trait StoryView extends AbstractView {
  def displayEvent(eventsList: List[String]): Unit

  def setNarrative(narrative: String): Unit

  def setPathways(pathways: Set[Pathway]): Unit
}

object StoryView {
  def apply(storyController: StoryController): StoryView = new StoryViewSwing(storyController)
}

private class StoryViewSwing(private val storyController: StoryController) extends StoryView {
  private var _narrative: String = ""
  private var _pathways: Seq[Pathway] = Seq()

  this.setLayout(new BorderLayout())

  override def setNarrative(narrative: String): Unit = _narrative = narrative

  override def setPathways(pathways: Set[Pathway]): Unit = _pathways = pathways.toSeq

  var eventsNameList: List[String] = List()

  override def displayEvent(eventsList: List[String]): Unit = {
    if (eventsList.nonEmpty) {
      SqSwingDialog("New Event!", eventsList.head, List(
        SqSwingButton("ok", (_: ActionEvent) => {
          displayEvent(eventsNameList)
        })),closable = false)
      eventsNameList = eventsList.drop(1)
    }
  }

  override def populateView(): Unit = {
    this.add(
      ControlsPanel(
        List(
          ("s", ("[S] Status", _ => storyController.goToStatStatus())),
          ("h", ("[H] History", _ => storyController.goToHistory())),
          ("p", ("[P] Save Progress", _ => storyController.goToProgressSaver())),
          ("q", ("[Q] Quit", _ => {
            SqSwingDialog("Exit Confirm", "Do you really want to exit the game?",
              List(new SqSwingButton("yes", (_: ActionEvent) => storyController.close(), true),
                new SqSwingButton("no", (_: ActionEvent) => {}, true)))
          })))),
      BorderLayout.NORTH
    )
    this.add(NarrativePanel(_narrative), BorderLayout.CENTER)
    this.add(PathwaysPanel(_pathways, p => storyController.choosePathWay(p)), BorderLayout.SOUTH)
  }

}
