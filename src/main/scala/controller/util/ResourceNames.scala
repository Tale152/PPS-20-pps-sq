package controller.util

/**
 * Component that stores the files and directories' name.
 */
object ResourceNames {

  private object DirectoryNames {
    val GameDirectoryName: String = ".sq"
    val StoryDirectoryName: String = "stories"
    val SoundsEffectsDirectoryName: String = "sound_effects"
    val StoryMusicDirectoryName: String = "story_music"
    val BattleMusicDirectoryName: String = "battle_music"
    val MenuMusicDirectoryName: String = "menu_music"
  }

  import controller.util.ResourceNames.FileExtensions.{StoryFileExtension, StoryProgressFileExtension}

  object FileExtensions {
    val StoryFileExtension: String = "sqstr"
    val StoryProgressFileExtension: String = "sqprg"
    val TxtExtension = "txt"
    val WavExtension = "wav"
    val PrologExtension = "pl"
  }

  private object SoundNames {
    import FileExtensions.WavExtension

    val InteractionSoundEffectFileName: String = "interaction." + WavExtension
    val NavigationSoundEffectFileName: String = "navigation." + WavExtension
  }

  private object PrologNames {
    import controller.util.ResourceNames.FileExtensions.PrologExtension

    val PrologTheoryFileName: String = "theory." + PrologExtension
  }

  object MainDirectory {
    val RootGameDirectory: String = System.getProperty("user.home")
    //use mostly for test purposes
    private val ScalaQuestTestFolderName = "ScalaQuest_Test"
    val TempDirectory: String = System.getProperty("java.io.tmpdir") + "/" + ScalaQuestTestFolderName
  }

  val testRandomStoryName: String = "test-random-story"

  import controller.util.ResourceNames.DirectoryNames._
  import controller.util.ResourceNames.MainDirectory._

  def storyDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    gameDirectoryPath(baseDirectory) + "/" + StoryDirectoryName

  def gameDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    baseDirectory + "/" + GameDirectoryName

  private def storyPathWitNoExtension(storyName: String)(baseDirectory: String): String =
    storyDirectoryPath(baseDirectory) + "/" + storyName + "/" + storyName

  def storyPath(storyName: String)(baseDirectory: String = RootGameDirectory): String =
    storyPathWitNoExtension(storyName)(baseDirectory) + "." + StoryFileExtension

  def storyProgressPath(storyName: String)(baseDirectory: String = RootGameDirectory): String =
    storyPathWitNoExtension(storyName)(baseDirectory) + "." + StoryProgressFileExtension

  import controller.util.ResourceNames.SoundNames._

  def interactionSoundEffectPath: String = "/" + SoundsEffectsDirectoryName + "/" + InteractionSoundEffectFileName

  def navigationSoundEffectPath: String = "/" + SoundsEffectsDirectoryName + "/" + NavigationSoundEffectFileName

  import controller.util.ResourceNames.PrologNames.PrologTheoryFileName

  def prologEngineTheoryPath: String = "/" + PrologTheoryFileName

  def storyMusicDirectoryPath(): String = {
    "/" + SoundsEffectsDirectoryName + "/" + StoryMusicDirectoryName
  }

  def battleMusicDirectoryPath(): String = {
    "/" + SoundsEffectsDirectoryName + "/" + BattleMusicDirectoryName
  }

  def menuMusicDirectoryPath(): String = {
    "/" + SoundsEffectsDirectoryName + "/" + MenuMusicDirectoryName
  }

}
