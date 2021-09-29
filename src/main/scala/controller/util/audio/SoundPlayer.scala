package controller.util.audio

import controller.util.Resources.SoundClip.interactionSoundClip
import controller.util.audio.MusicPlayer.stopMusic

import javax.sound.sampled.Clip

/**
 * Represents the sound effect in the game.
 */
object SoundPlayer {

  var isMute: Boolean = false

  /**
   * Stops all playing clips and plays a new one if program is not muted.
   *
   * @param clip the clip to play.
   * @param loop specifies if the clip has to loop or not.
   */
  def playClip(clip: Clip, loop: Boolean = false): Unit = {
    stopMusic()
    manageClip(clip, loop)
  }

  /**
   * Actually plays the clip, checks if it has to loop and set the starting point of the clip.
   *
   * @param clip the clip to play.
   * @param loop specifies if the clip has to loop or not.
   */
  def manageClip(clip: Clip, loop: Boolean = false): Unit = {
    if (!isMute) {
      clip.setFramePosition(0)
      if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY)
      clip.start()
    }
  }

  /**
   * Used to play a sound during the user interaction.
   */
  def playInteractionSound(): Unit = {
    manageClip(interactionSoundClip)
  }

}
