package controller.util

import ResourceName.{gameDirectoryPath, storyDirectoryPath}
import controller.util.DirectoryInitializer.FolderUtil.createFolderIfNotPresent
import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.ResourceName.MainDirectory.TempDirectory
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
    initializeGameFolderStructure(initializerTestDirectory)
    gameDirectory.isDirectory shouldBe true
    storiesDirectory.isDirectory shouldBe true
  }

}
