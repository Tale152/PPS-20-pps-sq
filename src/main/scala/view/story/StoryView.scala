package view.story

import controller.game.subcontroller.StoryController
import controller.util.audio.MusicPlayer.{playStoryMusic, stopMusic}
import controller.util.audio.SoundPlayer.isMute
import model.nodes.Pathway
import view.AbstractView
import view.story.panels.{NarrativePanel, PathwaysPanel}
import view.util.common.ControlsPanel
import view.util.common.StandardKeyListener.quitKeyListener
import view.util.scalaQuestSwingComponents.dialog.{SqOkSwingDialog, SqSwingDialog}
import view.util.scalaQuestSwingComponents.SqSwingButton

import java.awt.BorderLayout
import java.awt.event.{ActionEvent, ActionListener}

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
            ("s", ("[S] Status", _ => storyController.goToPlayerInfo())),
            ("h", ("[H] History", _ => storyController.goToHistory())),
            ("p", ("[P] Save Progress", _ => storyController.goToProgressSaver())),
            ("i", ("[I] Inventory", _ => storyController.goToInventory())),
            muteButton,
            quitKeyListener("Do you really want to exit the game?",
              (_: ActionEvent) => storyController.close()))
        ), BorderLayout.NORTH
      )
      this.add(NarrativePanel(_narrative), BorderLayout.CENTER)
      if (_pathways.nonEmpty) {
        this.add(PathwaysPanel(_pathways, p => storyController.choosePathway(p)), BorderLayout.SOUTH)
      } else {
        SqOkSwingDialog("Game over", "You reached a final node.", _ => {})
      }
    }

    private def muteButton: (String, (String, ActionListener)) = {
      ("m", ("[M] " + (if (isMute) {
        "UnMute"
      } else {
        "Mute"
      }), _ => {
        isMute = !isMute
        if (!isMute) {
          playStoryMusic()
        } else {
          stopMusic()
        }
        this.render()
      }))
    }
  }

  def apply(storyController: StoryController): StoryView = new StoryViewSwing(storyController)

}
