package controller.util

import controller.util.Resources.ResourceName.FileExtensions.PrologExtension
import controller.util.Resources.ResourceName.MainDirectory.RootGameDirectory
import controller.util.Resources.ResourceName.{interactionSoundEffectPath, navigationSoundEffectPath}

import java.awt.image.BufferedImage
import java.io.{BufferedInputStream, File, InputStream}

import java.net.{URI, URL}
import java.nio.file._

import javax.imageio.ImageIO
import javax.sound.sampled.{AudioInputStream, AudioSystem, Clip}
import scala.collection.JavaConverters.asScalaIteratorConverter
import scala.io.Source.fromInputStream

/**
 * Objects that contains resources like Audio and Images of the application.
 */
object Resources {

  /**
   * Implicit class used to add a method to map a[[java.io.InputStream]] to a [[javax.sound.sampled.AudioInputStream]].
   * @param inputStream the implicit InputStream parameter.
   */
  implicit class RichInputStream(inputStream: InputStream) {
    def toAudioInputStream: AudioInputStream = {
      //add buffer for mark/reset support
      val bufferedIn: InputStream = new BufferedInputStream(inputStream)
      AudioSystem.getAudioInputStream(bufferedIn)
    }
  }

  /**
   * Get the resource (from the resources folder) as an [[java.io.InputStream]].
   * @param resourcePath the resource path.
   * @return the [[java.io.InputStream]] representing the resource.
   */
  def resourceAsInputStream(resourcePath: String): InputStream = getClass.getResourceAsStream(resourcePath)

  /**
   * Convert all the resources inside a folder to a Set of [[java.io.InputStream]].
   * @param folderPath the folder path.
   * @return A set of [[java.io.InputStream]]
   */
  def resourcesAsInputStreamFromFolder(folderPath: String): Set[InputStream] = {
    val url: Option[URL] = Option(getClass.getResource(folderPath))
    url match {
      case Some(url) =>
        val uri: URI = url.toURI
        if (uri.getScheme.contains("jar")) {
          resourceAsInputStreamFromJarFolder(folderPath)
        } else {
          new File(uri).list().map(s => resourceAsInputStream(folderPath + "/" + s)).toSet
        }
      case None => Set()
    }
  }

  /**
   * Convert all the resources inside a folder to a Set of [[java.io.InputStream]].
   * Works only if launched from inside a compiled jar.
   * @param folderPath the folder path.
   * @return A set of [[java.io.InputStream]]
   */
  private def resourceAsInputStreamFromJarFolder(folderPath: String): Set[InputStream] = {
    val jarFile: Path = Paths.get(
      getClass.getProtectionDomain.getCodeSource.getLocation.toString.substring("file:".length())
    )
    val fs: FileSystem = FileSystems.newFileSystem(jarFile, getClass.getClassLoader)
    Files.newDirectoryStream(fs.getPath(folderPath))
      .iterator()
      .asScala
      .map(p => resourceAsInputStream(p.toString))
      .toSet
  }

  /**
   * @param resourceName the resource name.
   * @return a [[javax.sound.sampled.Clip]] containing the sound.
   */
  private def loadAudioClip(resourceName: String): Clip = {
    val clip = AudioSystem.getClip()
    clip.open(resourceAsInputStream(resourceName).toAudioInputStream)
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
