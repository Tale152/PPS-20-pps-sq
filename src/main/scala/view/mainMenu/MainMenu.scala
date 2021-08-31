package view.mainMenu

import controller.ApplicationController
import controller.ApplicationController.{loadStoryNewGame, loadStoryWithProgress}
import controller.util.ResourceName
import view.AbstractView
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.SqSwingButton.SqSwingButton
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingCenteredLabel}

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.nio.file.{Files, Paths}
import javax.swing.JOptionPane

trait MainMenu extends AbstractView {

  def setStories(stories: Set[String]): Unit

}

object MainMenu {
  def apply(applicationController: ApplicationController): MainMenu = new MainMenuImpl(applicationController)
}

private class MainMenuImpl(applicationController: ApplicationController) extends MainMenu {

  private object MainMenuValues {
    val LabelSize = 25
  }

  private var _stories: Set[String] = Set()
  this.setLayout(new BorderLayout())

  override def setStories(stories: Set[String]): Unit = _stories = stories

  override def populateView(): Unit = {
    import MainMenuValues._
    this.add(SqSwingCenteredLabel("Please select a story", size = LabelSize), BorderLayout.NORTH)
    this.add(Scrollable(VerticalButtons(generateButtons())))
    this.add(ControlsPanel(List(("q", ("[Q] Quit", _ => applicationController.close())))), BorderLayout.SOUTH)
  }

  private def generateButtons(): Set[SqSwingButton] = {
    for (storyName <- _stories) yield SqSwingButton("<html>" + storyName + "</html>", (_: ActionEvent) => {
        val storyPath = ResourceName.storyPath(storyName)
        val progressPath = ResourceName.storyProgressPath(storyName)
        if (Files.exists(Paths.get(progressPath))) {
          val jopRes = JOptionPane
            .showConfirmDialog(
              null,
              "Would you like to continue with your progresses?",
              "start",
              JOptionPane.YES_NO_OPTION
            )
          if (jopRes == JOptionPane.YES_OPTION) {
            loadStoryWithProgress(
              storyPath,
              progressPath
            )
          } else {
            loadStoryNewGame(storyPath)
          }
        } else {
          loadStoryNewGame(storyPath)
        }
      })
  }
}
