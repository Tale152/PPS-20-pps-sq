package controller

import controller.util.DirectoryInitializer.TestStoryPopulation
import controller.util.DirectoryInitializer.initializeGameFolderStructure
import controller.util.Resources.ResourceName._
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

  override def beforeAll(): Unit = {
    super.beforeAll()
    createFolderIfNotPresent(applicationControllerTestDirectory)
    initializeGameFolderStructure(applicationControllerTestDirectory, TestStoryPopulation())
  }

  "If no progress is available, false" should "be returned" taggedAs IgnoreGitHubAction in {
    ApplicationController.isProgressAvailable(testRandomStoryName)(applicationControllerTestDirectory) shouldBe false
  }

  "If progress is available, true" should "be returned" taggedAs IgnoreGitHubAction in {
    val progressTestFile = new File(storyProgressPath(testRandomStoryName)(applicationControllerTestDirectory))
    progressTestFile.createNewFile()
    ApplicationController.isProgressAvailable(testRandomStoryName)(applicationControllerTestDirectory) shouldBe true
  }


}
