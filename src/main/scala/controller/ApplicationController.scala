package controller

import controller.game.GameMasterController
import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.ResourceName
import model.nodes.util.StoryNodeSerializer
import model.nodes.util.StoryNodeSerializer.deserializeStory
import model.progress.ProgressSerializer

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
   * @param storyUri the uri where the story that will be loaded is located
   * @see [[PlayerConfigurationController]]
   * @see [[model.characters.properties.stats.Stat]]
   */
  def loadStoryNewGame(storyUri: String): Unit

  def loadStoryWithProgress(storyUri: String, progressUri: String): Unit
}

object ApplicationController extends ApplicationController {

  initializeGameFolderStructure(ResourceName.RootGameDirectory)

  override def execute(): Unit = {
    //in the future it will render the main menu
    if(Files.exists(Paths.get(ResourceName.storyDirectoryPath() + "/random-story.sqprg"))){
      loadStoryWithProgress(
        ResourceName.storyDirectoryPath() + "/random-story.ser",
        ResourceName.storyDirectoryPath() + "/random-story.sqprg"
      )
    } else {
      loadStoryNewGame(ResourceName.storyDirectoryPath() + "/random-story.ser")
    }
  }

  override def close(): Unit = System.exit(0)

  override def loadStoryNewGame(storyURI: String): Unit =
    PlayerConfigurationController(deserializeStory(storyURI)).execute()

  override def loadStoryWithProgress(storyUri: String, progressUri: String): Unit = {
    GameMasterController(
      ProgressSerializer.deserializeProgress(StoryNodeSerializer.deserializeStory(storyUri), progressUri)
    ).execute()
  }
}
