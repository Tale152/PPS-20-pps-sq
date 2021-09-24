package view.mainMenu

import controller.ApplicationController
import controller.ApplicationController.{isProgressAvailable, loadStoryNewGame, loadStoryWithProgress}
import controller.util.Resources.ResourceName
import controller.util.serialization.StringUtil.StringFormatUtil.formatted
import view.AbstractView
import view.mainMenu.buttonListeners._
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingLabel}

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.SwingConstants
import view.util.StringUtil.TitleSize

/**
 * Trait that represents the main menu of the game.
 */
sealed trait MainMenu extends AbstractView {

  /**
   * Method to display all the existing adventures.
   *
   * @param stories set of strings containing the adventures title.
   */
  def setStories(stories: Set[String]): Unit

}

object MainMenu {

  private class MainMenuImpl(applicationController: ApplicationController) extends MainMenu {

    private var _stories: Set[String] = Set()
    this.setLayout(new BorderLayout())

    override def setStories(stories: Set[String]): Unit = _stories = stories

    override def populateView(): Unit = {
      this.add(
        SqSwingLabel("Please select a story", labelSize = TitleSize, alignment = SwingConstants.CENTER),
        BorderLayout.NORTH
      )
      this.add(Scrollable(VerticalButtons(generateButtons())))
      this.add(ControlsPanel(
        List(
          ("l", ("[L] Load story", LoadStoryButtonListener(applicationController, this))),
          ("d", ("[D] Delete story", DeleteStoryButtonListener(applicationController))),
          ("e", ("[E] Editor", EditorButtonListener(applicationController, this))),
          ("q", ("[Q] Quit", QuitButtonListener(applicationController)))
        )
      ), BorderLayout.SOUTH)
    }

    private def generateButtons(): List[SqSwingButton] = {
      for (storyName <- _stories.toList) yield SqSwingButton(formatted(storyName), (_: ActionEvent) => {
        val storyPath = ResourceName.storyPath(storyName)()
        if (isProgressAvailable(storyName)()) {
          generateOptionPane(storyPath, ResourceName.storyProgressPath(storyName)())
        } else {
          loadStoryNewGame(storyPath)
        }
      })
    }

    private def generateOptionPane(storyPath: String, progressPath: String): Unit = {
      SqYesNoSwingDialog(
        "Load progress",
        "Would you like to continue with your progresses?",
        (_: ActionEvent) => loadStoryWithProgress(storyPath, progressPath),
        (_: ActionEvent) => loadStoryNewGame(storyPath))
    }
  }

  def apply(applicationController: ApplicationController): MainMenu = new MainMenuImpl(applicationController)

}
