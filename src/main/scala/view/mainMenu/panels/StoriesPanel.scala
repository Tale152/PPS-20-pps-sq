package view.mainMenu.panels

import controller.ApplicationController.{loadStoryNewGame, loadStoryWithProgress}
import controller.util.ResourceName
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingGridPanel}

import java.awt.event.ActionEvent
import java.nio.file.{Files, Paths}
import javax.swing.JOptionPane

object StoriesPanel {

  class StoriesPanel(stories: Set[String]) extends SqSwingGridPanel(0, 1) {
    stories.foreach(i => {
      val button = SqSwingButton(i, (_: ActionEvent) => {

        val storyPath = ResourceName.storyDirectoryPath() + "/" + i + "/" + i + ".sqstr"
        val progressPath = ResourceName.storyDirectoryPath() + "/" + i + "/" + i + ".sqprg"

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
      }
      )
      this.add(button)
    })
  }

  def apply(stories: Set[String]): StoriesPanel = new StoriesPanel(stories)

}
