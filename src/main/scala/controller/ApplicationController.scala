package controller

import controller.editor.EditorController
import controller.game.{GameMasterController, PlayerConfigurationController}
import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.ResourceLoader
import controller.util.Resources.ResourceName.MainDirectory.RootGameDirectory
import controller.util.Resources.ResourceName.{storyDirectoryPath, storyProgressPath}
import controller.util.FolderUtil.{deleteFolder, filesNameInFolder}
import controller.util.audio.MusicPlayer
import controller.util.serialization.ProgressSerializer
import controller.util.serialization.StoryNodeSerializer.deserializeStory
import model.nodes.StoryNode
import view.mainMenu.MainMenu
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

import java.io.File
import java.nio.file.{Files, Paths}

/**
 * The Application Controller is the Main Controller of the application.
 * It's the entrypoint and controls the main menu.
 */
sealed trait ApplicationController extends Controller {


  /**
   * Gives control to the [[controller.editor.EditorController]] that will manipulate the provided story.
   *
   * @param routeNode the route node of the story that will be manipulated
   */
  def goToEditor(routeNode: StoryNode): Unit

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
   * @param storyUri    the uri of the story to load.
   * @param progressUri the uri of the progress to load alongside the story.
   */
  def loadStoryWithProgress(storyUri: String, progressUri: String): Unit

  /**
   * Check if some progress is available for the selected story.
   *
   * @param storyName     the name of the story.
   * @param baseDirectory the parent directory name of the game folder.
   * @return true if progress is available, false otherwise.
   */
  def isProgressAvailable(storyName: String)(baseDirectory: String = RootGameDirectory): Boolean

  /**
   * Deletes an existing story's directory and files.
   *
   * @param directoryName the name of the story to delete.
   */
  def deleteExistingStory(directoryName: String): Unit
}

object ApplicationController extends ApplicationController {

  private val mainMenu: MainMenu = MainMenu(this)

  ResourceLoader.loadResources()
  initializeGameFolderStructure(RootGameDirectory)
  MusicPlayer.playMenuMusic()

  private def loadStoryNames(): Set[String] = filesNameInFolder(storyDirectoryPath())

  override def execute(): Unit = {
    mainMenu.setStories(loadStoryNames())
    mainMenu.render()
  }

  override def close(): Unit = System.exit(0)

  override def loadStoryNewGame(storyURI: String): Unit = {
    try {
      PlayerConfigurationController(ProgressSerializer.extractStoryName(storyURI), deserializeStory(storyURI)).execute()
    } catch {
      case _: Exception =>
        openErrorDialog()
    }
  }

  override def loadStoryWithProgress(storyUri: String, progressUri: String): Unit = {
    try {
      GameMasterController(
        ProgressSerializer.deserializeProgress(deserializeStory(storyUri), progressUri)
      ).execute()
    } catch {
      case _: Exception =>
        openErrorDialog()
    }
  }

  private def openErrorDialog(): Unit = {
    SqSwingDialog(
      "Error on story loading", "File structure is not suitable or corrupted",
      List(SqSwingButton("ok", _ => {}))
    )
  }

  override def isProgressAvailable(storyName: String)(baseDirectory: String = RootGameDirectory): Boolean =
    Files.exists(Paths.get(storyProgressPath(storyName)(baseDirectory)))

  override def goToEditor(routeNode: StoryNode): Unit = EditorController(routeNode).execute()

  override def deleteExistingStory(directoryName: String): Unit = {
    val directoryAbsolutePath: String = storyDirectoryPath(RootGameDirectory) + "/" + directoryName
    if (new File(directoryAbsolutePath).exists()) {
      deleteFolder(directoryAbsolutePath)
    }
  }

}
