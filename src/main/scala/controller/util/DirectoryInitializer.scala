package controller.util

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

    def _createDirectoryIfNotPresent(uri: String): Unit = {
      val directory: File = new File(uri)
      if (!directory.exists()) {
        directory.mkdir()
      }
    }

    def _populateStoriesDirectory(): Unit = {
      val deserializedStoryName: String = ResourceName.storyDirectoryPath(gameRootDirectory) + "/random-story.ser"
      serializeStory(RandomStoryNodeGenerator.generate(), deserializedStoryName)
    }

    _createDirectoryIfNotPresent(ResourceName.gameDirectoryPath(gameRootDirectory))
    _createDirectoryIfNotPresent(ResourceName.storyDirectoryPath(gameRootDirectory))
    if (new File(ResourceName.storyDirectoryPath(gameRootDirectory)).list().isEmpty) {
      _populateStoriesDirectory()
    }
  }

}
