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

  private val soundsEffectsDirectoryName: String = "sound_effects"
  private val interactionSoundEffectFileName: String = "interaction.wav"
  private val navigationSoundEffectFileName: String = "navigation.wav"

  // random story is hardcoded for now
  def randomStoryFileName(baseDirectory: String = RootGameDirectory): String =
    randomStoryDirectoryPath(baseDirectory) + "/" + randomStoryFileName

  def randomStoryProgressFileName(baseDirectory: String = RootGameDirectory): String =
    randomStoryDirectoryPath(baseDirectory) + "/" + randomStoryProgressFileName

  def randomStoryDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    storyDirectoryPath(baseDirectory) + "/" + randomStory

  def storyDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    gameDirectoryPath(baseDirectory) + "/" + StoryDirectoryName

  def gameDirectoryPath(baseDirectory: String = RootGameDirectory): String =
    baseDirectory + "/" + GameDirectoryName

  def interactionSoundEffectPath(): String = "/" + soundsEffectsDirectoryName + "/" + interactionSoundEffectFileName

  def navigationSoundEffectPath(): String = "/" + soundsEffectsDirectoryName + "/" + navigationSoundEffectFileName
}
