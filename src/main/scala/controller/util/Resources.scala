package controller.util

import controller.util.Resources.ResourceName.FileExtensions.PrologExtension
import controller.util.Resources.ResourceName.MainDirectory.RootGameDirectory
import controller.util.Resources.ResourceName.{interactionSoundEffectPath, navigationSoundEffectPath}

import java.awt.image.BufferedImage
import java.io.{BufferedInputStream, File, InputStream}
import javax.imageio.ImageIO
import javax.sound.sampled.{AudioInputStream, AudioSystem, Clip}
import scala.io.Source.fromInputStream

/**
 * Objects that contains resources like Audio and Images of the application.
 */
object Resources {

  def resourceAsInputStream(resourceName: String): InputStream = getClass.getResourceAsStream(resourceName)

  /**
   * @param resourceName the resource name.
   * @return a [[javax.sound.sampled.Clip]] containing the sound.
   */
  private def loadAudioClip(resourceName: String): Clip = {

    def _resourceAsAudioInputStream(resourceName: String): AudioInputStream = {
      val audioSrc: InputStream = resourceAsInputStream(resourceName)
      //add buffer for mark/reset support
      val bufferedIn: InputStream = new BufferedInputStream(audioSrc)
      AudioSystem.getAudioInputStream(bufferedIn)
    }

    val clip = AudioSystem.getClip()
    clip.open(_resourceAsAudioInputStream(resourceName))
    clip
  }

  /**
   * Loads all existing clips.
   *
   * @param resourcesName set containing the name of the clips to load.
   * @return a set of clip already loaded.
   */
  def loadAudioClips(resourcesName: Set[String]): Set[Clip] = {
    resourcesName.map(n => loadAudioClip(n))
  }

  /**
   * @param resourceName the resource name.
   * @return a [[java.awt.image.BufferedImage]]
   */
  def loadImage(resourceName: String): BufferedImage = {
    ImageIO.read(resourceAsInputStream(resourceName))
  }

  /**
   * @param filePath the path of a resource file.
   * @return a list containing the lines of the file.
   */
  def resourcesAsLines(filePath: String): List[String] = {
    fromInputStream(resourceAsInputStream(filePath)).getLines().toList
  }

  object SoundClip {
    lazy val interactionSoundClip: Clip = loadAudioClip(interactionSoundEffectPath)
    lazy val navigationSoundClip: Clip = loadAudioClip(navigationSoundEffectPath)
  }

  /**
   * Component that stores the files and directories' name.
   */
  object ResourceName {

    private object DirectoryNames {
      val GameDirectoryName: String = ".sq"
      val StoryDirectoryName: String = "stories"
      val SoundsEffectsDirectoryName: String = "sound_effects"
      val StoryMusicDirectoryName: String = "story_music"
      val BattleMusicDirectoryName: String = "battle_music"
      val MenuMusicDirectoryName: String = "menu_music"
    }

    import controller.util.Resources.ResourceName.FileExtensions.{StoryFileExtension, StoryProgressFileExtension}

    object FileExtensions {
      val StoryFileExtension: String = "sqstr"
      val StoryProgressFileExtension: String = "sqprg"
      val WavExtension = "wav"
      val PrologExtension = "pl"
    }

    private object SoundNames {

      import FileExtensions.WavExtension

      val InteractionSoundEffectFileName: String = "interaction." + WavExtension
      val NavigationSoundEffectFileName: String = "navigation." + WavExtension
    }

    private object PrologNames {
      val PrologTheoryFileName: String = "theory." + PrologExtension
    }

    object MainDirectory {
      val RootGameDirectory: String = System.getProperty("user.home")
      //use mostly for test purposes
      private val ScalaQuestTestFolderName = "ScalaQuest_Test"
      val TempDirectory: String = System.getProperty("java.io.tmpdir") + "/" + ScalaQuestTestFolderName
    }

    val testRandomStoryName: String = "test-random-story"

    import controller.util.Resources.ResourceName.DirectoryNames.{GameDirectoryName, SoundsEffectsDirectoryName, StoryDirectoryName}

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

    import controller.util.Resources.ResourceName.SoundNames.{InteractionSoundEffectFileName, NavigationSoundEffectFileName}

    def interactionSoundEffectPath: String = "/" + SoundsEffectsDirectoryName + "/" + InteractionSoundEffectFileName

    def navigationSoundEffectPath: String = "/" + SoundsEffectsDirectoryName + "/" + NavigationSoundEffectFileName

    import controller.util.Resources.ResourceName.PrologNames.PrologTheoryFileName

    def prologEngineTheoryPath: String = "/" + PrologTheoryFileName

    import controller.util.Resources.ResourceName.DirectoryNames.{BattleMusicDirectoryName, MenuMusicDirectoryName, StoryMusicDirectoryName}

    def storyMusicDirectoryPath(): String = {
      "/" + SoundsEffectsDirectoryName + "/" + StoryMusicDirectoryName
    }

    def battleMusicDirectoryPath(): String = {
      "/" + SoundsEffectsDirectoryName + "/" + BattleMusicDirectoryName
    }

    def menuMusicDirectoryPath(): String = {
      "/" + SoundsEffectsDirectoryName + "/" + MenuMusicDirectoryName
    }

    def musicFileNames(path: String): Set[String] =
      new File(getClass.getResource(path).toURI).list.map(name => path + "/" + name).toSet

  }
}
