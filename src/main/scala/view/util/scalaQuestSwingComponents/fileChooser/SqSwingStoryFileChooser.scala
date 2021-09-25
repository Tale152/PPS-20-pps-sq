package view.util.scalaQuestSwingComponents.fileChooser

import controller.util.ResourceNames
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * Represents a custom FileChooser for ScalaQuest.
 *
 * @param title the title of the FileChooser.
 */
case class SqSwingStoryFileChooser(title: String) extends AbstractSqSwingFileChooser(title) {
  this.setAcceptAllFileFilterUsed(false)
  this.addChoosableFileFilter(
    new FileNameExtensionFilter(
      "." + ResourceNames.FileExtensions.StoryFileExtension,
      ResourceNames.FileExtensions.StoryFileExtension
    )
  )

}
