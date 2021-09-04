package controller.util

import java.io.{BufferedInputStream, InputStream}
import javax.sound.sampled.{AudioInputStream, AudioSystem}

/**
 * Component that stores the files and directories' name.
 */
object ResourceName {

  private object DirectoryNames {
    val GameDirectoryName: String = ".sq"
    val StoryDirectoryName: String = "stories"
    val SoundsEffectsDirectoryName: String = "sound_effects"
  }

  object FileExtensions {
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
    private val ScalaQuestTestFolderName = "ScalaQuest_Test"
    val TempDirectory: String = System.getProperty("java.io.tmpdir") + "/" + ScalaQuestTestFolderName
  }

  val testRandomStoryName: String = "test-random-story"

  import controller.util.ResourceName.DirectoryNames._
  import controller.util.ResourceName.MainDirectory.RootGameDirectory

  def storyDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    gameDirectoryPath(baseDirectory) + "/" + StoryDirectoryName

  def gameDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    baseDirectory + "/" + GameDirectoryName

  private def storyPathWitNoExtension(storyName: String)(baseDirectory: String): String =
    storyDirectoryPath(baseDirectory) + "/" + storyName + "/" + storyName

  import controller.util.ResourceName.FileExtensions._

  def storyPath(storyName: String)(baseDirectory: String = RootGameDirectory): String =
    storyPathWitNoExtension(storyName)(baseDirectory) + "." + StoryFileExtension

  def storyProgressPath(storyName: String)(baseDirectory: String = RootGameDirectory): String =
    storyPathWitNoExtension(storyName)(baseDirectory) + "." + StoryProgressFileExtension

  import controller.util.ResourceName.SoundNames._

  def interactionSoundEffectPath(): String = "/" + SoundsEffectsDirectoryName + "/" + InteractionSoundEffectFileName

  def navigationSoundEffectPath(): String = "/" + SoundsEffectsDirectoryName + "/" + NavigationSoundEffectFileName

  def resourceAsInputStream(resourceName: String): InputStream = getClass.getResourceAsStream(resourceName)

  def resourceAsAudioInputStream(resourceName: String): AudioInputStream = {
    val audioSrc : InputStream = resourceAsInputStream(resourceName)
    //add buffer for mark/reset support
    val bufferedIn : InputStream = new BufferedInputStream(audioSrc)
    AudioSystem.getAudioInputStream(bufferedIn)
  }

}
