package controller.util


import controller.util.ResourceNames.interactionSoundEffectPath
import java.awt.image.BufferedImage
import java.io.{BufferedInputStream, File, InputStream}
import java.net.{URI, URL}
import java.nio.charset.StandardCharsets
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
   * Implicit class used to add a method to map a [[java.io.InputStream]] to a [[javax.sound.sampled.AudioInputStream]].
   *
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
   *
   * @param resourcePath the resource path.
   * @return the [[java.io.InputStream]] representing the resource.
   */
  def resourceAsInputStream(resourcePath: String): InputStream = getClass.getResourceAsStream(resourcePath)

  /**
   * Convert all the resources inside a folder to a Set of (FileName, [[java.io.InputStream]]).
   *
   * @param folderPath the folder path.
   * @return A set of (FileName, [[java.io.InputStream]]).
   */
  def resourcesAsNamedInputStreamFromFolder(folderPath: String): Set[(String, InputStream)] = {
    val url: Option[URL] = Option(getClass.getResource(folderPath))
    url match {
      case Some(url) =>
        val uri: URI = url.toURI
        if (uri.getScheme.contains("jar")) {
          resourceAsInputStreamFromJarFolder(folderPath)
        } else {
          new File(uri).list().map(n => (n, resourceAsInputStream(folderPath + "/" + n))).toSet
        }
      case None => Set()
    }
  }

  /**
   * Convert all the resources inside a folder to a Set of [[java.io.InputStream]].
   *
   * @param folderPath the folder path.
   * @return A set of [[java.io.InputStream]]
   */
  def resourcesAsInputStreamFromFolder(folderPath: String): Set[InputStream] =
    resourcesAsNamedInputStreamFromFolder(folderPath).map(t => t._2)

  /**
   * Convert all the resources inside a folder to a Set of (FileName, [[java.io.InputStream]]).
   * Works only if launched from inside a compiled jar.
   *
   * @param folderPath the folder path.
   * @return A set of (FileName, [[java.io.InputStream]])
   */
  private def resourceAsInputStreamFromJarFolder(folderPath: String): Set[(String, InputStream)] = {
    val decodedFileSystemURL: String = {
      java.net.URLDecoder.decode(
        getClass.getProtectionDomain.getCodeSource.getLocation.toString.substring("file:".length())
          .replaceFirst("^/(.:/)", "$1"),
        StandardCharsets.UTF_8.name
      )
    }
    val fs = FileSystems.newFileSystem(Paths.get(decodedFileSystemURL), getClass.getClassLoader)
    Files.newDirectoryStream(fs.getPath(folderPath))
      .iterator()
      .asScala
      .map(p => (p.getFileName.toString, resourceAsInputStream(p.toString)))
      .toSet
  }

  /**
   * Loads a clip from it's corresponding inputstream.
   *
   * @param inputStream stream representing the audio file.
   * @return a loaded clip.
   */
  private def loadClipFromAudioInputStream(inputStream: InputStream): Clip = {
    val audioInputStream: AudioInputStream = inputStream match {
      case a: AudioInputStream => a
      case _ => inputStream.toAudioInputStream
    }
    val clip = AudioSystem.getClip()
    clip.open(audioInputStream)
    clip
  }

  /**
   * @param resourceName the resource name.
   * @return a [[javax.sound.sampled.Clip]] containing the sound.
   */
  private def loadAudioClip(resourceName: String): Clip = {
    loadClipFromAudioInputStream(resourceAsInputStream(resourceName))
  }

  /**
   * Loads all existing clips.
   *
   * @param resourcesFolder string containing the name of the folder that contains the music.
   * @return a set of clip already loaded.
   */
  def loadAudioClips(resourcesFolder: String): Set[Clip] = {
    resourcesAsInputStreamFromFolder(resourcesFolder).map(i => loadClipFromAudioInputStream(i))
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
  }

}
