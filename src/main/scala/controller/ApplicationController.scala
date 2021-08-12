package controller

import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.ResourceName
import model.nodes.util.StoryNodeSerializer.deserializeStory

sealed trait ApplicationController extends Controller {

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
    StatConfigurationController(deserializeStory(storyURI)).execute()
}
