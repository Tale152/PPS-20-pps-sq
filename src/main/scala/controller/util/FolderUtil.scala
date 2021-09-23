package controller.util

import java.io.File

object FolderUtil {

  /**
   * Create a folder if it does not exist.
   *
   * @param uri the uri of the new folder.
   */
  def createFolderIfNotPresent(uri: String): Unit = {
    val directory: File = new File(uri)
    if (!directory.exists()) {
      directory.mkdir()
    }
  }

  /**
   * @param folderPath the path to the target folder
   * @return a list of file names in the target folder
   */
  def filesNameInFolder(folderPath: String): Set[String] = {
    new File(folderPath).list().toSet
  }

  /**
   * Delete a folder and all its content recursively.
   *
   * @param folderName the path of folder where to start.
   */
  def deleteFolder(folderName: String): Unit = {
    val folder = new File(folderName)
    if (folder.exists()) {
      val files = folder.listFiles
      if (files != null) { //some JVMs return null for empty dirs
        for (f <- files) {
          if (f.isDirectory) {
            deleteFolder(f.getPath)
          } else {
            f.delete
          }
        }
      }
      folder.delete
    }
  }
}
