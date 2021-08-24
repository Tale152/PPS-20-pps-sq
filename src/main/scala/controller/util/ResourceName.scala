package controller.util

/**
 * Component that stores the files and directories' name.
 */
object ResourceName {

  private val GameDirectoryName: String = ".sq"
  private val StoryDirectoryName: String = "stories"

  val RootGameDirectory: String = System.getProperty("user.home")

  //use mostly for test purposes
  val TempDirectory: String = System.getProperty("java.io.tmpdir")

  private val randomStory: String = "random-story"
  private val randomStoryFileName: String = "random-story.sqstr"
  private val randomStoryProgressFileName: String = "random-story.sqprg"

  def testRandomStoryFileName(baseDirectory: String = RootGameDirectory): String =
    testRandomStoryDirectoryPath(baseDirectory) + "/" + randomStoryFileName

  def testRandomStoryProgressFileName(baseDirectory: String = RootGameDirectory): String =
    testRandomStoryDirectoryPath(baseDirectory) + "/" + randomStoryProgressFileName

  def testRandomStoryDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    storyDirectoryPath(baseDirectory) + "/" + randomStory

  def storyDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    gameDirectoryPath(baseDirectory) + "/" + StoryDirectoryName

  def gameDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    baseDirectory + "/" + GameDirectoryName
}
