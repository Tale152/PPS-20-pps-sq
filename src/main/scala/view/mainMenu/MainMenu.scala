package view.mainMenu

import controller.ApplicationController
import controller.ApplicationController.{isProgressAvailable, loadStoryNewGame, loadStoryWithProgress}
import controller.util.Resources.ResourceName
import controller.util.serialization.StoryNodeSerializer.deserializeStory
import model.nodes.StoryNode
import view.AbstractView
import view.util.SqFileChooser
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingLabel}
import view.util.scalaQuestSwingComponents.dialog.{SqSwingDialog, SqYesNoSwingDialog}
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.SwingConstants
import javax.swing.JFileChooser

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
      this.add(ControlsPanel(List(
        ("q", ("[Q] Quit", _ => applicationController.close())),
        ("e", ("[E] Editor", _ => {
          SqSwingDialog(
            "ScalaQuest",
            "Do you want to create a new story or load an existing one?",
            List(
              SqSwingButton("New story", _ => ApplicationController.goToEditor(
                StoryNode(0, "start", None, Set(), List()))
              ),
              SqSwingButton("Load story", _ => {
                val chooser = SqFileChooser.getFileChooser("Load story")
                if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                  ApplicationController.goToEditor(deserializeStory(chooser.getSelectedFile.getPath))
                }
              })
            )
          )
        }))
      )), BorderLayout.SOUTH)
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
