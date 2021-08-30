package view.util

import controller.util.ResourceName

import java.io.File
import javax.sound.sampled.AudioSystem

object SoundPlayer {

  private def playAudio(path: String): Unit = {
    val clip = AudioSystem.getClip
    clip.open(
      AudioSystem.getAudioInputStream(
        new File(getClass.getResource(path).getPath)
      )
    )
    clip.start()
  }

  def playNavigationSound(): Unit = {
    playAudio(ResourceName.navigationSoundEffectPath())
  }

  def playInteractionSound(): Unit = {
    playAudio(ResourceName.interactionSoundEffectPath())
  }
}
