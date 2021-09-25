package view.mainMenu.buttonListeners

import controller.ApplicationController
import controller.util.ResourceNames.{FileExtensions, storyDirectoryPath}
import controller.util.ResourceNames.MainDirectory.RootGameDirectory
import controller.util.FolderUtil.createFolderIfNotPresent
import controller.util.serialization.DeserializerChecker.checkOnLoadingFile
import controller.util.serialization.StoryNodeSerializer.{deserializeStory, serializeStory}
import view.mainMenu.MainMenu
import view.mainMenu.buttonListeners.MainMenuButtonListeners.LoadStoryChooserButtonListener
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog
import java.awt.event.ActionEvent
import java.io.File

import controller.util.ResourceNames.FileExtensions.StoryFileExtension
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * [[view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuButtonListener]] used for the Load Story Button
 * in the Main Menu.
 *
 * @param applicationController The Application Controller.
 * @param mainMenu              the reference to the Main Menu.
 */
case class LoadStoryButtonListener(override val applicationController: ApplicationController, mainMenu: MainMenu)
  extends LoadStoryChooserButtonListener(applicationController) {

  override def actionPerformed(e: ActionEvent): Unit = {
    loadStoryFileChooser.setFileFilter(new FileNameExtensionFilter("SQSTR", StoryFileExtension))
    loadStoryFileChooser.showOpenDialog(mainMenu)
    val file: Option[File] = Option(loadStoryFileChooser.getSelectedFile)
    if (file.nonEmpty) {
      loadStoryFromFile(file.get)
    }
  }

  private def loadStoryFromFile(file: File): Unit = {
    val nameWithOutExtension =
      file.getName.substring(0, file.getName.length - FileExtensions.StoryFileExtension.length - 1)
    val newStoryFolderPath = storyDirectoryPath(RootGameDirectory) + "/" + nameWithOutExtension
    if (new File(newStoryFolderPath).exists()) {
      overrideStoryDialog(file, newStoryFolderPath, nameWithOutExtension)
    } else {
      loadNewStory(file, newStoryFolderPath)
    }
  }

  private def overrideStoryDialog(file: File, newStoryFolderPath: String, nameWithOutExtension: String) = {
    SqSwingDialog("Story already present", "Do you want to override existing story?",
      List(
        SqSwingButton("ok", _ => {
          new File(newStoryFolderPath + "/" + nameWithOutExtension + "." + StoryFileExtension).delete()
          loadNewStory(file, newStoryFolderPath)
        }),
        SqSwingButton("cancel", _ => {
          /*does nothing*/
        })))
  }

  private def loadNewStory(file: File, newStoryFolderPath: String): Unit = {
    checkOnLoadingFile(() => {
      val deserialized = deserializeStory(file.getPath)
      createFolderIfNotPresent(newStoryFolderPath)
      serializeStory(deserialized, newStoryFolderPath + "/" + file.getName)
      ApplicationController.execute()
    }, "Error on story loading")
  }

}