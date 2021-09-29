package suites

import controller.ApplicationControllerTest
import controller.util.DirectoryInitializerTest
import controller.util.ResourceNames.MainDirectory.TempDirectory
import controller.util.FolderUtil.{createFolderIfNotPresent, deleteFolder}
import org.scalatest.{BeforeAndAfterAll, Suites}

/**
 * Test Suite that contains tests that needs a folder in the FileSystem.
 */
class FileSystemSuite extends Suites(new ApplicationControllerTest, new DirectoryInitializerTest)
  with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    super.beforeAll()
    createFolderIfNotPresent(TempDirectory)
  }

  override def afterAll() {
    super.afterAll()
    deleteFolder(TempDirectory)
  }
}
