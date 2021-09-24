package controller.util

import view.util.scalaQuestSwingComponents.SqFont

import javax.swing.UIManager

/**
 * Object used to load certain objects at application startup.
 */
object ResourceLoader {

  /**
   * Load objects that are used by the application very often.
   */
  def loadResources(): Unit = {
    setLookAndFeel()
    //Load Audio Clips
    Resources.SoundClip.interactionSoundClip
    //Load font
    SqFont(bold = false)
  }

  private def setLookAndFeel(): Unit = {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
