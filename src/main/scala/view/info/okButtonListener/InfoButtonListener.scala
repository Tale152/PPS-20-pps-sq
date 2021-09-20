package view.info.okButtonListener

import view.util.scalaQuestSwingComponents.fileChooser.SqSwingDirectoryChooser

/**
 * An Info Button will always save a file in the filesystem.
 */
trait InfoButtonListener {

  /**
   * @return a condition. If true, the file creation phase will be next.
   */
  def fileSaveCondition: () => Boolean

  /**
   * Save the file in the folder 'folderPath'.
   * @param folderPath the path of the folder chosen by the user.
   */
  def saveFile(folderPath: String): Unit

  /**
   * Show an error if [[view.info.okButtonListener.InfoButtonListener#fileSaveCondition()]] is not satisfied.
   */
  def showError(): Unit

  /**
   * The action to perform is always structured in the same way.
   */
  def performAction(): Unit = {
    if(fileSaveCondition()) {
      SqSwingDirectoryChooser(
        "Select folder where to save the file",
        folderPath => saveFile(folderPath)
      )
    } else{
      showError()
    }
  }

}


