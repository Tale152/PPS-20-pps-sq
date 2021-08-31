package controller.util

import ResourceName.{gameDirectoryPath, storyDirectoryPath}
import controller.util.DirectoryInitializer.FolderUtil.{createFolderIfNotPresent, deleteFolder}
import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.ResourceName.MainDirectory.TempDirectory
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import specs.FlatTestSpec

import java.io.File

class DirectoryInitializerTest extends FlatTestSpec with BeforeAndAfterEach with BeforeAndAfterAll {

  val initializerTestDirectory: String = TempDirectory + "/initializer"
  val gameDirectory = new File(gameDirectoryPath(initializerTestDirectory))
  val storiesDirectory = new File(storyDirectoryPath(initializerTestDirectory))

  override def beforeEach(): Unit = {
    super.beforeEach()
    deleteFolder(initializerTestDirectory)
    createFolderIfNotPresent(initializerTestDirectory)
  }

  override def afterAll(): Unit = {
    super.afterAll()
    deleteFolder(initializerTestDirectory)
  }

  "The directories" should "not exist before the initialization" in {
    println(gameDirectory.getPath)
    println(storiesDirectory.getPath)
    gameDirectory.exists() shouldBe false
    storiesDirectory.exists() shouldBe false
    initializeGameFolderStructure(initializerTestDirectory)
    gameDirectory.isDirectory shouldBe true
    storiesDirectory.isDirectory shouldBe true
  }

}
