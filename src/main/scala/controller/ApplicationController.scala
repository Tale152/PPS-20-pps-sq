package controller

import controller.game.GameMasterController
import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.ResourceName
import controller.util.serialization.ProgressSerializer

import java.nio.file.{Files, Paths}
import controller.util.serialization.StoryNodeSerializer.deserializeStory

import javax.swing.JOptionPane


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
    if(Files.exists(Paths.get(ResourceName.randomStoryProgressFileName()))){
      val jopRes = JOptionPane
        .showConfirmDialog(
          null,
          "Would you like to continue with your progresses?",
          "start",
          JOptionPane.YES_NO_OPTION
        )
      if(jopRes == JOptionPane.YES_OPTION){
        loadStoryWithProgress(
          ResourceName.randomStoryFileName(),
          ResourceName.randomStoryProgressFileName()
        )
      } else {
        loadStoryNewGame(ResourceName.randomStoryFileName())
      }
    } else {
      loadStoryNewGame(ResourceName.randomStoryFileName())
    }
  }

  override def close(): Unit = System.exit(0)

  override def loadStoryNewGame(storyURI: String): Unit =
    PlayerConfigurationController(deserializeStory(storyURI)).execute()

  override def loadStoryWithProgress(storyUri: String, progressUri: String): Unit = {
    GameMasterController(
      ProgressSerializer.deserializeProgress(deserializeStory(storyUri), progressUri)
    ).execute()
  }
}
