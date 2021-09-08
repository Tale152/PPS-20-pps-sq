package view.util

import controller.util.Resources.AudioClip.{interactionSoundClip, navigationSoundClip}

import javax.sound.sampled.Clip

/**
 * Represents the sound effect in the game.
 */
object SoundPlayer {

  private def playClip(clip: Clip): Unit = {
    clip.setFramePosition(0)
    clip.start()
  }

  /**
   * Used to play a sound effect during navigation.
   */
  def playNavigationSound(): Unit = {
    playClip(navigationSoundClip)
  }

  /**
   * Used to play a sound during the user interaction.
   */
  def playInteractionSound(): Unit = {
    playClip(interactionSoundClip)
  }
}
