package view.mainMenu.panels

import controller.ApplicationController.{loadStoryNewGame, loadStoryWithProgress}
import controller.util.ResourceName
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingPanel}

import java.awt.event.ActionEvent
import java.awt.{GridBagConstraints, GridBagLayout, Insets}
import java.nio.file.{Files, Paths}
import javax.swing.JOptionPane

object StoriesPanel {

  class StoriesPanel(stories: Set[String]) extends SqSwingPanel() {

    this.setLayout(new GridBagLayout)
    val c = new GridBagConstraints

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
      })

      import java.awt.GridBagConstraints

      val buttonHeight = 30
      val topPadding = 10
      c.fill = GridBagConstraints.HORIZONTAL
      c.ipady = buttonHeight
      c.weightx = 0.0
      c.gridwidth = 3
      c.gridx = 0
      c.insets = new Insets(topPadding, 0, 0, 0) //top padding
      this.add(button, c)
    })
  }

  def apply(stories: Set[String]): StoriesPanel = new StoriesPanel(stories)

}
