package view.util.scalaQuestSwingComponents.fileChooser

import controller.util.Resources.ResourceName

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
      "." + ResourceName.FileExtensions.StoryFileExtension,
      ResourceName.FileExtensions.StoryFileExtension
    )
  )


}
