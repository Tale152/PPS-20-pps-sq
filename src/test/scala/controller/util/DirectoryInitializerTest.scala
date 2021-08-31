package controller.util

import ResourceName.{gameDirectoryPath, storyDirectoryPath}
import controller.util.ResourceName.MainDirectory.TempDirectory
import org.scalatest.BeforeAndAfterEach
import specs.FlatTestSpec
import util.TestUtil.deleteFolder

import java.io.File

class DirectoryInitializerTest extends FlatTestSpec with BeforeAndAfterEach {

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
