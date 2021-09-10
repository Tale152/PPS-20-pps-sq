package view.mainMenu.buttonListeners

import controller.ApplicationController
import controller.util.Resources.ResourceName.{FileExtensions, storyDirectoryPath}
import controller.util.Resources.ResourceName.MainDirectory.RootGameDirectory
import controller.util.serialization.FolderUtil.createFolderIfNotPresent
import controller.util.serialization.StoryNodeSerializer.{deserializeStory, serializeStory}
import view.mainMenu.MainMenu
import view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuChooserButtonListener
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

import java.awt.event.ActionEvent
import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter

case class LoadStoryButtonListener(override val applicationController: ApplicationController, mainMenu: MainMenu)
  extends MainMenuChooserButtonListener(applicationController) {

  override def actionPerformed(e: ActionEvent): Unit = {
    fileChooser.setFileFilter(new FileNameExtensionFilter("SQSTR", "sqstr"))
    fileChooser.showOpenDialog(mainMenu)
    val file: File = fileChooser.getSelectedFile
    if (file != null) {
      val nameWithOutExtension =
        file.getName.substring(0, file.getName.length - FileExtensions.StoryFileExtension.length - 1)
      val newStoryFolderPath = storyDirectoryPath(RootGameDirectory) +
        "/" + nameWithOutExtension
      if (new File(newStoryFolderPath).exists()) {
        SqSwingDialog("Story already present", "Do you want to override existing story?",
          List(
            SqSwingButton("ok", _ => {
              new File(newStoryFolderPath + "/" + nameWithOutExtension + ".sqprg").delete()
              loadNewStory(file, newStoryFolderPath)
            }),
            SqSwingButton("cancel", _ => {
              /*does nothing*/
            })))
      } else {
        loadNewStory(file, newStoryFolderPath)
      }
    }
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

}