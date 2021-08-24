package controller

import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.ResourceName
import controller.util.serialization.StoryNodeSerializer.deserializeStory

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
}

object ApplicationController extends ApplicationController {

  initializeGameFolderStructure(ResourceName.RootGameDirectory)

  /**
   * Start the Controller.
   */
  override def execute(): Unit = {
    //in the future it will render the main menu
    loadStoryNewGame(ResourceName.storyDirectoryPath() + "/random-story.ser")
  }

  /**
   * Defines the actions to do when the Controller execution is over.
   */
  override def close(): Unit = System.exit(0)

  override def loadStoryNewGame(storyURI: String): Unit =
    PlayerConfigurationController(deserializeStory(storyURI)).execute()
}
