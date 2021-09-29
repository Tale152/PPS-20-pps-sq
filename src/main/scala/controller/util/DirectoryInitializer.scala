package controller.util

import controller.util.ResourceNames.FileExtensions.StoryFileExtension
import controller.util.ResourceNames._
import FolderUtil.{createFolderIfNotPresent, filesNameInFolder}
import java.nio.file.{Files, Paths, StandardCopyOption}

import controller.util.Resources.resourcesAsNamedInputStreamFromFolder

object DirectoryInitializer {

  /**
   * Choose how to populate the story folder on first run.
   */
  sealed trait StoryPopulation extends (String => Unit)

  /** Don't insert any story. */
  case class NoStoryPopulation() extends StoryPopulation {
    def apply(gameRootDirectory: String): Unit = {/* does nothing */}
  }

  /** Insert the default set of stories. */
  case class DefaultStoryPopulation() extends StoryPopulation {
    def apply(gameRootDirectory: String): Unit = {
      resourcesAsNamedInputStreamFromFolder(defaultStoriesDirectoryPath)
        .foreach(i => {
          val storyFolder: String = (storyDirectoryPath(gameRootDirectory) + "/" + i._1)
            .dropRight(StoryFileExtension.length + 1)
          createFolderIfNotPresent(storyFolder)
          Files.copy(i._2, Paths.get(storyFolder + "/" + i._1), StandardCopyOption.REPLACE_EXISTING)
        })
    }
  }

  /**
   * Create the directory tree if it does not exist yet and populate the stories folder with stock stories.
   *
   * @param gameRootDirectory the root directory where all game data are stored.
   */
  def initializeGameFolderStructure(gameRootDirectory: String,
                                    populate: StoryPopulation = DefaultStoryPopulation()): Unit = {
    createFolderIfNotPresent(gameDirectoryPath(gameRootDirectory))
    createFolderIfNotPresent(storyDirectoryPath(gameRootDirectory))
    if (filesNameInFolder(storyDirectoryPath(gameRootDirectory)).isEmpty) {
      populate(gameRootDirectory)
    }
  }
}
