package view.util.scalaQuestSwingComponents

import controller.util.Resources.ResourceName

import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.{JComponent, JFileChooser}

/**
 * Represents a custom FileChooser for ScalaQuest.
 *
 * @param title the title of the FileChooser.
 */
case class SqSwingFileChooser(title: String) extends JFileChooser {
  this.setDialogTitle(title)
  this.setAcceptAllFileFilterUsed(false)
  this.addChoosableFileFilter(
    new FileNameExtensionFilter(
      "." + ResourceName.FileExtensions.StoryFileExtension,
      ResourceName.FileExtensions.StoryFileExtension
    )
  )



}