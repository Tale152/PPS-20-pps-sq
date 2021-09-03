package controller

import controller.game.GameMasterController
import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.ResourceLoader
import controller.util.Resources.ResourceName.MainDirectory.RootGameDirectory
import controller.util.Resources.ResourceName.{storyDirectoryPath, storyProgressPath}
import controller.util.serialization.ProgressSerializer
import controller.util.serialization.StoryNodeSerializer.deserializeStory
import view.mainMenu.MainMenu

import java.io.File
import java.nio.file.{Files, Paths}

/**
 * The Application Controller is the Main Controller of the application.
 * It's the entrypoint and controls the main menu.
 */
sealed trait ApplicationController extends Controller {

  /**
   * Load a story starting a new game. Once the story is loaded the control will be granted to the
   * StatConfigurationController in order to allow the user to choose his stats.
   *
   * @param storyUri the uri where the story that will be loaded is located.
   * @see [[PlayerConfigurationController]]
   * @see [[model.characters.properties.stats.Stat]]
   */
  def loadStoryNewGame(storyUri: String): Unit

  /**
   * Resumes an already started story with previously saved data.
   *
   * @param storyUri the uri of the story to load.
   * @param progressUri the uri of the progress to load alongside the story.
   */
  def loadStoryWithProgress(storyUri: String, progressUri: String): Unit


  /**
   * Check if some progress is available for the selected story.
   * @param storyName the name of the story.
   * @param baseDirectory the parent directory name of the game folder.
   * @return true if progress is available, false otherwise.
   */
  def isProgressAvailable(storyName: String)(baseDirectory: String = RootGameDirectory): Boolean
}

object ApplicationController extends ApplicationController {

  ResourceLoader.loadResources()
  private val mainMenu: MainMenu = MainMenu(this)

  private def loadStoryNames(): Set[String] = {
    new File(storyDirectoryPath()).list().toSet
  }

  initializeGameFolderStructure(RootGameDirectory)

  override def execute(): Unit = {
    mainMenu.setStories(loadStoryNames())
    mainMenu.render()
  }

  override def close(): Unit = System.exit(0)

  override def loadStoryNewGame(storyURI: String): Unit = {
    PlayerConfigurationController(deserializeStory(storyURI)).execute()
  }

  override def loadStoryWithProgress(storyUri: String, progressUri: String): Unit = {
    GameMasterController(
      ProgressSerializer.deserializeProgress(deserializeStory(storyUri), progressUri)
    ).execute()
  }

  /**
   * Check if some progress is available for the selected story.
   *
   * @param storyName     the name of the story.
   * @param baseDirectory the parent directory name of the game folder.
   * @return true if progress is available, false otherwise.
   */
  override def isProgressAvailable(storyName: String)(baseDirectory: String = RootGameDirectory): Boolean =
    Files.exists(Paths.get(storyProgressPath(storyName)(baseDirectory)))

}
