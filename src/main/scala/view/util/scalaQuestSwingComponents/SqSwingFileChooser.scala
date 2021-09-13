package view.util.scalaQuestSwingComponents

import controller.util.Resources.ResourceName

import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.{JComponent, JFileChooser}

/**
 * Utility object used to get and show elements in a FileChooser.
 */
object SqSwingFileChooser {

  def showFileSave(title: String, onSave: String => Unit, selectedFileName: String, parent: JComponent): Unit = {
    val chooser = SqSwingFileChooser(title)
    chooser.setSelectedFile(new File(selectedFileName))
    if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
      onSave(chooser.getSelectedFile.getPath)
    }
  }

}

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