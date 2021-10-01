package controller

import controller.editor.EditorController
import controller.game.{GameMasterController, PlayerConfigurationController}
import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.ResourceLoader
import controller.util.ResourceNames.MainDirectory.RootGameDirectory
import controller.util.ResourceNames.{FileExtensions, storyDirectoryPath, storyProgressPath}
import controller.util.FolderUtil.{createFolderIfNotPresent, deleteFolder, filesNameInFolder}
import controller.util.audio.MusicPlayer
import controller.util.serialization.ProgressSerializer
import controller.util.Checker.ActionChecker
import controller.util.ResourceNames.FileExtensions.StoryFileExtension
import controller.util.serialization.StoryNodeSerializer.{deserializeStory, serializeStory}
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
   * Add a new story to the stories collection.
   *
   * @param storyFile a file containing a story to add.
   */
  def addNewStory(storyFile: File): Unit

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
    val deserializationOfStory: () => Unit = () => PlayerConfigurationController(
      ProgressSerializer.extractStoryName(storyURI),
      deserializeStory(storyURI)).execute()
    val deserializationError: () => Unit = () => mainMenu.showDeserializationError("Error on story loading")

    deserializationOfStory ifFails deserializationError
  }

  override def loadStoryWithProgress(storyUri: String, progressUri: String): Unit = {
    val deserializationOfStory: () => Unit =
      () => GameMasterController(
        ProgressSerializer.deserializeProgress(deserializeStory(storyUri), progressUri)
      ).execute()
    val deserializationError: () => Unit =
      () => mainMenu.showDeserializationError("Error on story loading story and progress")

    deserializationOfStory ifFails deserializationError
  }

  override def isProgressAvailable(storyName: String)(baseDirectory: String = RootGameDirectory): Boolean =
    Files.exists(Paths.get(storyProgressPath(storyName)(baseDirectory)))

  override def deleteExistingStory(directoryName: String): Unit = {
    val directoryAbsolutePath: String = storyDirectoryPath(RootGameDirectory) + "/" + directoryName
    if (new File(directoryAbsolutePath).exists()) {
      deleteFolder(directoryAbsolutePath)
    }
  }

  override def goToEditor(routeNode: StoryNode): Unit = EditorController(routeNode).execute()

  override def addNewStory(storyFile: File): Unit = {
    val nameWithOutExtension =
      storyFile.getName.substring(0, storyFile.getName.length - FileExtensions.StoryFileExtension.length - 1)
    val newStoryFolderPath = storyDirectoryPath(RootGameDirectory) + "/" + nameWithOutExtension
    if (new File(newStoryFolderPath).exists()) {
      overrideStoryDialog(storyFile, newStoryFolderPath, nameWithOutExtension)
    } else {
      loadNewStory(storyFile, newStoryFolderPath)
    }
  }

  private def overrideStoryDialog(file: File, newStoryFolderPath: String, nameWithOutExtension: String) = {
    SqSwingDialog("Story already present", "Do you want to override existing story?",
      List(
        SqSwingButton("ok", _ => {
          new File(newStoryFolderPath + "/" + nameWithOutExtension + "." + StoryFileExtension).delete()
          loadNewStory(file, newStoryFolderPath)
        }),
        SqSwingButton("cancel", _ => {
          /*does nothing*/
        })))
  }

  private def loadNewStory(file: File, newStoryFolderPath: String): Unit = {
    val deserializeNewStory: () => Unit =
      () => {
        createFolderIfNotPresent(newStoryFolderPath)
        serializeStory(deserializeStory(file.getPath), newStoryFolderPath + "/" + file.getName)
        ApplicationController.execute()
      }
    val deserializationError: () => Unit =
      () => mainMenu.showDeserializationError("Error on adding story.")

    deserializeNewStory ifFails deserializationError
  }

}
