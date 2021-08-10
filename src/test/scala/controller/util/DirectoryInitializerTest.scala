package controller.util

import ResourceName.{TempDirectory, gameDirectoryPath, storyDirectoryPath}
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import util.TestUtil.deleteFolder

import java.io.File

class DirectoryInitializerTest extends FlatSpec with Matchers with BeforeAndAfterEach {

  val gameDirectory = new File(gameDirectoryPath(TempDirectory))
  val storiesDirectory = new File(storyDirectoryPath(TempDirectory))

  override def beforeEach(): Unit = {
    super.beforeEach()
    deleteFolder(gameDirectory)
  }

  "The directories" should "not exist before the initialization" in {
    gameDirectory.exists() shouldBe false
    storiesDirectory.exists() shouldBe false
    DirectoryInitializer.initializeGameFolderStructure(TempDirectory)
    gameDirectory.isDirectory shouldBe true
    storiesDirectory.isDirectory shouldBe true
  }

}
