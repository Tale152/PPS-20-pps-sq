package controller.util.audio

import controller.util.Resources.SoundClip.interactionSoundClip

import javax.sound.sampled.Clip

/**
 * Represents the sound effect in the game.
 */
object SoundPlayer {

  def playClip(clip: Clip, loop: Boolean = false): Unit = {
    clip.setFramePosition(0)
    if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY)
    clip.start()
  }

  /**
   * Used to play a sound during the user interaction.
   */
  def playInteractionSound(): Unit = {
    playClip(interactionSoundClip)
  }

}
