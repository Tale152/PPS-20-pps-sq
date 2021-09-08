package view.mainMenu

import controller.ApplicationController
import controller.ApplicationController.{isProgressAvailable, loadStoryNewGame, loadStoryWithProgress}
import controller.util.Resources.ResourceName
import controller.util.Resources.ResourceName.MainDirectory.RootGameDirectory
import controller.util.Resources.ResourceName.{FileExtensions, storyDirectoryPath}
import controller.util.serialization.FolderUtil.createFolderIfNotPresent
import controller.util.serialization.StoryNodeSerializer.{deserializeStory, serializeStory}
import model.nodes.StoryNode
import view.AbstractView
import view.editor.forms.DeleteStory.showDeleteStoryForm
import view.util.SqFileChooser
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.dialog.{SqSwingDialog, SqYesNoSwingDialog}
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingLabel}

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.{JFileChooser, SwingConstants}

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
      val chooser = SqFileChooser.getFileChooser("Load story")
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
              SqSwingButton("Load story", _ =>
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                  ApplicationController.goToEditor(deserializeStory(chooser.getSelectedFile.getPath))
                })))
        })), ("l", ("[L] Load story", _ => {
          chooser.setFileFilter(new FileNameExtensionFilter("SQSTR", "sqstr"))
          chooser.showOpenDialog(this)
          val file: File = chooser.getSelectedFile
          if (file != null) {
            val nameWithOutExtension =
              file.getName.substring(0, file.getName.length - FileExtensions.StoryFileExtension.length - 1)
            val newStoryFolderPath = storyDirectoryPath(RootGameDirectory) +
              "/" + nameWithOutExtension
            if (new File(newStoryFolderPath).exists()) {
              SqSwingDialog("Story already present", "Do you want to override existing story?",
                List(SqSwingButton("ok", _ => {
                  new File(newStoryFolderPath + "/" + nameWithOutExtension + ".sqprg").delete()
                  loadNewStory(file, newStoryFolderPath)
                }), SqSwingButton("cancel", _ => {
                  /*does nothing*/
                })))
            } else {
              loadNewStory(file, newStoryFolderPath)
            }
          }
        })), ("d", ("[D] Delete story", _ => {
          showDeleteStoryForm(applicationController,
            new File(storyDirectoryPath(RootGameDirectory)).listFiles().toList.map(f => f.getName))
        }))
      )), BorderLayout.SOUTH)
    }

    private def loadNewStory(file: File, newStoryFolderPath: String): Unit = {
      try {
        val deserialized = deserializeStory(file.getPath)
        createFolderIfNotPresent(newStoryFolderPath)
        serializeStory(deserialized, newStoryFolderPath + "/" + file.getName)
        ApplicationController.execute()
      } catch {
        case _: Exception =>
          println("File structure is not suitable or corrupted")
          SqSwingDialog("Error on story loading", "File structure is not suitable or corrupted",
            List(SqSwingButton("ok", _ => {
              /*does nothing*/
            })))
      }
    }

    private def generateButtons(): List[SqSwingButton] = {
      for (storyName <- _stories.toList) yield SqSwingButton("<html>" + storyName + "</html>", (_: ActionEvent) => {
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
