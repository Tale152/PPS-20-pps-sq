package view.mainMenu

import controller.ApplicationController
import controller.ApplicationController.{isProgressAvailable, loadStoryNewGame, loadStoryWithProgress}
import controller.util.ResourceNames
import controller.util.StringUtil.StringFormatUtil.swingFormatted
import view.AbstractView
import view.mainMenu.buttonListeners._
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingLabel}

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.SwingConstants
import view.util.StringUtil.TitleSize
import view.util.common.StandardKeyListener.quitKeyListener

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
          quitKeyListener("Do you really want to exit the game?",
            _ => applicationController.close()),
        )
      ), BorderLayout.SOUTH)
    }

    private def generateButtons(): List[SqSwingButton] = {
      for (storyName <- _stories.toList) yield SqSwingButton(swingFormatted(storyName),
        (_: ActionEvent) => {
          val storyPath = ResourceNames.storyPath(storyName)()
          if (isProgressAvailable(storyName)()) {
            generateOptionPane(storyPath, ResourceNames.storyProgressPath(storyName)())
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
