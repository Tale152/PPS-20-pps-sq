package view.mainMenu

import controller.ApplicationController
import controller.ApplicationController.{isProgressAvailable, loadStoryNewGame, loadStoryWithProgress}
import controller.util.ResourceName
import view.AbstractView
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingLabel}

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.SwingConstants


trait MainMenu extends AbstractView {

  def setStories(stories: Set[String]): Unit

}

object MainMenu {

  private class MainMenuImpl(applicationController: ApplicationController) extends MainMenu {
    private object MainMenuValues {
      val LabelSize = 25
    }
    private var _stories: Set[String] = Set()
    this.setLayout(new BorderLayout())

    override def setStories(stories: Set[String]): Unit = _stories = stories

    override def populateView(): Unit = {
      import MainMenuValues._
      this.add(
        SqSwingLabel("Please select a story", labelSize = LabelSize, alignment = SwingConstants.CENTER),
        BorderLayout.NORTH
      )
      this.add(Scrollable(VerticalButtons(generateButtons())))
      this.add(ControlsPanel(List(("q", ("[Q] Quit", _ => applicationController.close())))), BorderLayout.SOUTH)
    }

    private def generateButtons(): Set[SqSwingButton] = {
      for (storyName <- _stories) yield SqSwingButton("<html>" + storyName + "</html>", (_: ActionEvent) => {
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
