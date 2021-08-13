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

  def storyDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    gameDirectoryPath(baseDirectory) + "/" + StoryDirectoryName

  def gameDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    baseDirectory + "/" + GameDirectoryName
}
