package view.util

import controller.util.Resources.AudioClip.{interactionSoundClip, navigationSoundClip}

import javax.sound.sampled.Clip

object SoundPlayer {

  def playClip(clip: Clip ): Unit = {
    clip.setFramePosition(0)
    clip.start()
  }

  def playNavigationSound(): Unit = {
    playClip(navigationSoundClip)

  }

  def playInteractionSound(): Unit = {
    playClip(interactionSoundClip)
  }
}
