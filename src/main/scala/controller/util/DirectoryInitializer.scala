package controller.util

import controller.util.Resources.{ResourceName, resourcesAsNamedInputStreamFromFolder}
import controller.util.Resources.ResourceName.{
  defaultStoriesDirectoryPath,
  gameDirectoryPath,
  storyDirectoryPath,
  testRandomStoryName
}
import FolderUtil.{createFolderIfNotPresent, filesNameInFolder}
import controller.util.Resources.ResourceName.FileExtensions.StoryFileExtension
import controller.util.serialization.StoryNodeSerializer.serializeStory
import model.nodes.util.RandomStoryNodeGenerator

import java.nio.file.{Files, Path, StandardCopyOption}


object DirectoryInitializer {

  /**
   * Choose how to populate the story folder on first run.
   */
  sealed trait StoryPopulationStrategy extends (String => Unit)

  /** Don't insert any story. */
  case class NoStoryPopulation() extends StoryPopulationStrategy {
    def apply(gameRootDirectory: String): Unit = {/* does nothing */}
  }

  /** Insert a test story. */
  case class TestStoryPopulation() extends StoryPopulationStrategy {
    def apply(gameRootDirectory: String): Unit = {
      createFolderIfNotPresent(storyDirectoryPath(gameRootDirectory) + "/" + testRandomStoryName)
      serializeStory(
        RandomStoryNodeGenerator.Generator.generate(),
        ResourceName.storyPath(testRandomStoryName)(gameRootDirectory)
      )
    }
  }

  /** Insert the default set of stories. */
  case class DefaultStoryPopulation() extends StoryPopulationStrategy {
    def apply(gameRootDirectory: String): Unit = {
      resourcesAsNamedInputStreamFromFolder(defaultStoriesDirectoryPath)
        .foreach(i => {
          val storyFolder: String = (storyDirectoryPath(gameRootDirectory) + "/" + i._1)
            .dropRight(StoryFileExtension.length + 1)
          createFolderIfNotPresent(storyFolder)
          Files.copy(i._2, Path.of(storyFolder + "/" + i._1), StandardCopyOption.REPLACE_EXISTING)
        })
    }
  }

  private def createStoryFolderAndFile(storyName: String): Unit = {

  }

  /**
   * Create the directory tree if it does not exist yet and populate the stories folder with stock stories.
   *
   * @param gameRootDirectory the root directory where all game data are stored.
   */
  def initializeGameFolderStructure(gameRootDirectory: String,
                                    populationStrategy: StoryPopulationStrategy = DefaultStoryPopulation()): Unit = {
    createFolderIfNotPresent(gameDirectoryPath(gameRootDirectory))
    createFolderIfNotPresent(storyDirectoryPath(gameRootDirectory))
    if (filesNameInFolder(storyDirectoryPath(gameRootDirectory)).isEmpty) {
      populationStrategy(gameRootDirectory)
    }
  }
}
