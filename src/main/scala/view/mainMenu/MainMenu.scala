package view.mainMenu

import controller.ApplicationController
import controller.ApplicationController.{loadStoryNewGame, loadStoryWithProgress}
import controller.util.ResourceName
import view.AbstractView
import view.util.common.{ControlsPanel, ScrollableVerticalButtons}
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
  private var _stories: Set[String] = Set()
  private val lblSize = 25
  this.setLayout(new BorderLayout())

  override def setStories(stories: Set[String]): Unit = _stories = stories

  override def populateView(): Unit = {
    this.add(SqSwingCenteredLabel("Please select a story", size = lblSize), BorderLayout.NORTH)
    this.add(ScrollableVerticalButtons(generateButtons()))
    this.add(ControlsPanel(List(("q", ("[Q] Quit", _ => applicationController.close())))), BorderLayout.SOUTH)
  }

  private def generateButtons(): Set[SqSwingButton] ={
    for(s <- _stories)
      yield SqSwingButton("<html>" + s + "</html>", (_: ActionEvent) => {
        val storyPath = ResourceName.storyDirectoryPath() + "/" + s + "/" + s + ".sqstr"
        val progressPath = ResourceName.storyDirectoryPath() + "/" + s + "/" + s + ".sqprg"
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
