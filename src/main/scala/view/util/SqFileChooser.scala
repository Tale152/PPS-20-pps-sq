package view.util

import controller.util.ResourceName

import java.io.File
import javax.swing.{JComponent, JFileChooser}
import javax.swing.filechooser.FileNameExtensionFilter

object SqFileChooser {

  def showFileSave(title: String, onSave: String => Unit, selectedFileName: String, parent: JComponent): Unit = {
    val chooser = getFileChooser(title)
    chooser.setSelectedFile(new File(selectedFileName))
    if(chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
      onSave(chooser.getSelectedFile.getPath)
    }
  }

  def getFileChooser(title: String): JFileChooser = {
    val chooser = new JFileChooser()
    chooser.setDialogTitle(title)
    chooser.setAcceptAllFileFilterUsed(false)
    chooser.addChoosableFileFilter(
      new FileNameExtensionFilter(
        "." + ResourceName.FileExtensions.StoryFileExtension,
        ResourceName.FileExtensions.StoryFileExtension
      )
    )
    chooser
  }

}
