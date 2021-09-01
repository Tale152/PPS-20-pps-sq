package view.util

import controller.util.ResourceName.{interactionSoundEffectPath, navigationSoundEffectPath, resourceAsAudioInputStream}

import javax.sound.sampled.{AudioInputStream, AudioSystem, Clip}

object SoundPlayer {

  private def playAudio(audioInputStream: AudioInputStream): Unit = {
    val clip : Clip = AudioSystem.getClip()
    clip.open(audioInputStream)
    clip.start()
  }

  def playNavigationSound(): Unit = {
    playAudio(resourceAsAudioInputStream(navigationSoundEffectPath()))
  }

  def playInteractionSound(): Unit = {
    playAudio(resourceAsAudioInputStream(interactionSoundEffectPath()))
  }
}
