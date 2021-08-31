package controller

import controller.util.DirectoryInitializer.FolderUtil.{createFolderIfNotPresent, deleteFolder}
import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.ResourceName.MainDirectory.TempDirectory
import controller.util.ResourceName.{gameDirectoryPath, randomStoryName, storyDirectoryPath, storyProgressPath}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import specs.FlatTestSpec

import java.io.File

class ApplicationControllerTest extends FlatTestSpec with BeforeAndAfterEach with BeforeAndAfterAll {

  val applicationControllerTestDirectory : String  = TempDirectory + "/applicationController"
  val gameDirectory = new File(gameDirectoryPath(applicationControllerTestDirectory))
  val storiesDirectory = new File(storyDirectoryPath(applicationControllerTestDirectory))

  override def beforeEach(): Unit = {
    super.beforeEach()
    deleteFolder(applicationControllerTestDirectory)
    createFolderIfNotPresent(applicationControllerTestDirectory)
    initializeGameFolderStructure(applicationControllerTestDirectory)
  }

  override def afterAll(): Unit = {
    super.afterAll()
    deleteFolder(applicationControllerTestDirectory)
  }

  "If no progress is available, false" should "be returned" in {
    ApplicationController.isProgressAvailable(randomStoryName, applicationControllerTestDirectory) shouldBe false
  }

  "If progress is available, true" should "be returned" in {
    val progressTestFile = new File(storyProgressPath(randomStoryName, applicationControllerTestDirectory))
    progressTestFile.createNewFile()
    ApplicationController.isProgressAvailable(randomStoryName, applicationControllerTestDirectory) shouldBe true
  }


}
