package util

import java.io.File

/**
 * Use by some tests to interact with the filesystem.
 */
object TestUtil {

  /**
   * Delete a folder and all its content recursively.
   * @param folder the folder where to start.
   */
  def deleteFolder(folder: File): Unit = {
    val files = folder.listFiles
    if (files != null) { //some JVMs return null for empty dirs
      for (f <- files) {
        if (f.isDirectory) {
          deleteFolder(f)
        } else {
          f.delete
        }
      }
    }
    folder.delete
  }
}
