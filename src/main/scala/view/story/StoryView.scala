package view.story

import controller.game.subcontroller.StoryController
import model.nodes.Pathway
import view.AbstractView
import view.util.common.ControlsPanel

import java.awt.BorderLayout

/**
 * Represents the GUI for the navigation between [[model.nodes.StoryNode]]
 */
trait StoryView extends AbstractView {
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

  override def populateView(): Unit = {
    this.add(
      ControlsPanel(
        List(
          ("s", ("[S] Status", _ => storyController.goToStatStatus())),
          ("h", ("[H] History", _ => storyController.goToHistory())),
          ("p", ("[P] Save Progress", _ => storyController.goToProgressSaver())),
          ("q", ("[Q] Quit", _ => storyController.close()))
        )
      ),
      BorderLayout.NORTH
    )
    this.add(NarrativePanel(_narrative), BorderLayout.CENTER)
    val pathwaysPanel = PathwaysPanel(_pathways, p => storyController.choosePathWay(p))
    this.add(pathwaysPanel, BorderLayout.SOUTH)
  }
}
