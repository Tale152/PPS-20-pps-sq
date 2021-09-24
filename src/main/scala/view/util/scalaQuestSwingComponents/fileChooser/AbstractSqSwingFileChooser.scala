package view.util.scalaQuestSwingComponents.fileChooser

import javax.swing.JFileChooser

/**
 * [[javax.swing.JFileChooser#JFileChooser]] that has a title.
 * @param title the title of the [[javax.swing.JFileChooser#JFileChooser]].
 */
abstract class AbstractSqSwingFileChooser(title: String) extends JFileChooser {
  this.setDialogTitle(title)
}
