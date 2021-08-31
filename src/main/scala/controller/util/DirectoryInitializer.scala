package controller.util

import controller.util.DirectoryInitializer.FolderUtil.createFolderIfNotPresent
import controller.util.ResourceName.{gameDirectoryPath, randomStoryName, storyDirectoryPath}
import controller.util.serialization.StoryNodeSerializer.serializeStory
import model.nodes.util.RandomStoryNodeGenerator

import java.io.File

object DirectoryInitializer {

  /**
   * Create a the directory tree if it does not exist yet and populate the stories folder with stock stories.
   *
   * @param gameRootDirectory the root directory where all game data are stored.
   */
  def initializeGameFolderStructure(gameRootDirectory: String): Unit = {

    def _populateStoriesDirectory(gameRootDirectory: String): Unit = {
      createFolderIfNotPresent(storyDirectoryPath(gameRootDirectory) + "/" + randomStoryName)
      serializeStory(RandomStoryNodeGenerator.generate(), ResourceName.storyPath(randomStoryName))
    }

    createFolderIfNotPresent(gameDirectoryPath(gameRootDirectory))
    createFolderIfNotPresent(storyDirectoryPath(gameRootDirectory))
    if (new File(storyDirectoryPath(gameRootDirectory)).list().isEmpty) {
      _populateStoriesDirectory(gameRootDirectory)
    }
  }

  object FolderUtil {

    /**
     * Create a folder if it does not exist.
     * @param uri the uri of the new folder.
     */
    def createFolderIfNotPresent(uri: String): Unit = {
      val directory: File = new File(uri)
      if (!directory.exists()) {
        directory.mkdir()
      }
    }

    /**
     * Delete a folder and all its content recursively.
     * @param folderName the path of folder where to start.
     */
    def deleteFolder(folderName: String): Unit = {
      val folder = new File(folderName)
      if(folder.exists()){
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
}
