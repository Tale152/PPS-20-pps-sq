package controller.util

import controller.util.DirectoryInitializer.StoryPopulationStrategy.{DefaultStoryPopulation, StoryPopulationStrategy}
import controller.util.ResourceName.{gameDirectoryPath, storyDirectoryPath, testRandomStoryName}
import controller.util.serialization.FolderUtil.createFolderIfNotPresent
import controller.util.serialization.StoryNodeSerializer.serializeStory
import model.nodes.util.RandomStoryNodeGenerator

import java.io.File

object DirectoryInitializer {

  object StoryPopulationStrategy {

    /**
     * Choose how to populate the story folder on first run.
     */
    sealed trait StoryPopulationStrategy {
      def apply(gameRootDirectory: String): Unit
    }

    /** Don't insert any story. */
    case class NoStoryPopulation() extends StoryPopulationStrategy {
      def apply(gameRootDirectory: String): Unit = {/* does nothing */}
    }

    /** Insert a test story. */
    case class TestStoryPopulation() extends StoryPopulationStrategy {
      def apply(gameRootDirectory: String): Unit = {
        serializeStory(
          RandomStoryNodeGenerator.generate(),
          ResourceName.storyPath(testRandomStoryName)(gameRootDirectory)
        )
      }
    }

    /** Insert the default set of stories. */
    case class DefaultStoryPopulation() extends StoryPopulationStrategy {
      def apply(gameRootDirectory: String): Unit = {
        //TODO change this to the default set of story on final release.
        serializeStory(
          RandomStoryNodeGenerator.generate(),
          ResourceName.storyPath(testRandomStoryName)(gameRootDirectory)
        )
      }
    }
  }

  /**
   * Create a the directory tree if it does not exist yet and populate the stories folder with stock stories.
   *
   * @param gameRootDirectory the root directory where all game data are stored.
   */
  def initializeGameFolderStructure(gameRootDirectory: String,
                                    populationStrategy: StoryPopulationStrategy = DefaultStoryPopulation()): Unit = {

    def _populateStoriesDirectory(gameRootDirectory: String, populationStrategy: StoryPopulationStrategy): Unit = {
      createFolderIfNotPresent(storyDirectoryPath(gameRootDirectory) + "/" + testRandomStoryName)
      populationStrategy(gameRootDirectory)
    }

    createFolderIfNotPresent(gameDirectoryPath(gameRootDirectory))
    createFolderIfNotPresent(storyDirectoryPath(gameRootDirectory))
    if (new File(storyDirectoryPath(gameRootDirectory)).list().isEmpty) {
      _populateStoriesDirectory(gameRootDirectory, populationStrategy)
    }
  }
}
