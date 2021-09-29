package controller.util

import controller.util.DirectoryInitializer.{DefaultStoryPopulation, initializeGameFolderStructure}
import controller.util.ResourceNames.MainDirectory.TempDirectory
import controller.util.ResourceNames._
import FolderUtil.createFolderIfNotPresent
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec

import java.io.File

/**
 * Tested in [[suites.FileSystemSuite]].
 */
@DoNotDiscover
class DirectoryInitializerTest extends FlatTestSpec {

  val initializerTestDirectory: String = TempDirectory + "/initializer"
  val gameDirectory = new File(gameDirectoryPath(initializerTestDirectory))
  val storiesDirectory = new File(storyDirectoryPath(initializerTestDirectory))

  "The directories" should "not exist before the initialization" in {
    createFolderIfNotPresent(initializerTestDirectory)
    gameDirectory.exists() shouldBe false
    storiesDirectory.exists() shouldBe false
    initializeGameFolderStructure(initializerTestDirectory, DefaultStoryPopulation())
    gameDirectory.isDirectory shouldBe true
    storiesDirectory.isDirectory shouldBe true
  }

}
