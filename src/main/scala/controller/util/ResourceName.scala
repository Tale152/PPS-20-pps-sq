package controller.util

/**
 * Component that stores the files and directories' name.
 */
object ResourceName {

  private object DirectoryNames {
    val GameDirectoryName: String = ".sq"
    val StoryDirectoryName: String = "stories"
    val SoundsEffectsDirectoryName: String = "sound_effects"
  }

  private object FileExtensions {
    val StoryFileExtension: String = "sqstr"
    val StoryProgressFileExtension: String = "sqprg"
    val WavExtension = "wav"
  }

  private object SoundNames {
    import FileExtensions.WavExtension
    val InteractionSoundEffectFileName: String = "interaction." + WavExtension
    val NavigationSoundEffectFileName: String = "navigation." + WavExtension
  }

  object MainDirectory{
    val RootGameDirectory: String = System.getProperty("user.home")
    //use mostly for test purposes
    val TempDirectory: String = System.getProperty("java.io.tmpdir")
  }

  val randomStoryName: String = "random-story"

  import controller.util.ResourceName.DirectoryNames._
  import controller.util.ResourceName.MainDirectory.RootGameDirectory

  def storyDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    gameDirectoryPath(baseDirectory) + "/" + StoryDirectoryName

  def gameDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    baseDirectory + "/" + GameDirectoryName

  private def storyPathWitNoExtension(storyName: String, baseDirectory: String): String =
    storyDirectoryPath(baseDirectory) + "/" + storyName + "/" + storyName

  import controller.util.ResourceName.FileExtensions._

  def storyPath(storyName: String, baseDirectory: String = RootGameDirectory): String =
    storyPathWitNoExtension(storyName, baseDirectory) + "." + StoryFileExtension

  def storyProgressPath(storyName: String, baseDirectory: String = RootGameDirectory): String =
    storyPathWitNoExtension(storyName, baseDirectory) + "." + StoryProgressFileExtension

  import controller.util.ResourceName.SoundNames._

  def interactionSoundEffectPath(): String = "/" + SoundsEffectsDirectoryName + "/" + InteractionSoundEffectFileName

  def navigationSoundEffectPath(): String = "/" + SoundsEffectsDirectoryName + "/" + NavigationSoundEffectFileName
}
