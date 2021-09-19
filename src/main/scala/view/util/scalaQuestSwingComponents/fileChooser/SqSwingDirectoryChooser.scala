package view.util.scalaQuestSwingComponents.fileChooser

import java.io.File
import javax.swing.JFileChooser

/**
 * [[view.util.scalaQuestSwingComponents.fileChooser.AbstractSqSwingFileChooser]] used to select a folder where to
 * accomplish operations.
 * @param title The JFileChooser title.
 * @param afterChooseAction The action to perform after choosing the path.
 */
case class SqSwingDirectoryChooser(title: String,
                              afterChooseAction: String => Unit)
  extends AbstractSqSwingFileChooser(title) {
  this.setApproveButtonText("Select")
  this.setApproveButtonToolTipText("Select a folder")
  this.setCurrentDirectory(new File("."))
  this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
  this.setAcceptAllFileFilterUsed(false)
  if (this.showOpenDialog(this.getParent) == JFileChooser.APPROVE_OPTION) {
    afterChooseAction(this.getSelectedFile.getAbsolutePath)
  }
}
