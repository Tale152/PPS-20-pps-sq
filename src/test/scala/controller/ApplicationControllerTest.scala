package controller

import controller.util.DirectoryInitializer.{DefaultStoryPopulation, initializeGameFolderStructure}
import controller.util.ResourceNames._
import controller.util.FolderUtil.createFolderIfNotPresent
import org.scalatest.{BeforeAndAfterAll, DoNotDiscover}
import specs.FlatTestSpec
import specs.Tags.IgnoreGitHubAction

import java.io.File

/**
 * Tested in [[suites.FileSystemSuite]].
 */
@DoNotDiscover
class ApplicationControllerTest extends FlatTestSpec with BeforeAndAfterAll {
  val applicationControllerTestDirectory : String  = MainDirectory.TempDirectory + "/applicationController"
  val gameDirectory = new File(gameDirectoryPath(applicationControllerTestDirectory))
  val storiesDirectory = new File(storyDirectoryPath(applicationControllerTestDirectory))
  val tutorialStoryName = "Tutorial"

  override def beforeAll(): Unit = {
    super.beforeAll()
    createFolderIfNotPresent(applicationControllerTestDirectory)
    initializeGameFolderStructure(applicationControllerTestDirectory, DefaultStoryPopulation())
  }

  "If no progress is available, false" should "be returned" taggedAs IgnoreGitHubAction in {
    ApplicationController.isProgressAvailable(tutorialStoryName)(applicationControllerTestDirectory) shouldBe false
  }

  "If progress is available, true" should "be returned" taggedAs IgnoreGitHubAction in {
    val progressTestFile = new File(storyProgressPath(tutorialStoryName)(applicationControllerTestDirectory))
    progressTestFile.createNewFile()
    ApplicationController.isProgressAvailable(tutorialStoryName)(applicationControllerTestDirectory) shouldBe true
  }

}
