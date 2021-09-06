package controller.util

/**
 * Object used to load certain objects at application startup.
 */
object ResourceLoader {

  /**
   * Load objects that are used by the application very often.
   */
  def loadResources(): Unit = {
    Resources.AudioClip.interactionSoundClip
    Resources.AudioClip.navigationSoundClip
  }

}
