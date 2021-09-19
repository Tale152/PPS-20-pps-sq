package controller.util

import controller.util.Resources.MusicClip.{battleMusic, menuMusic, storyMusic}
import controller.util.Resources.SoundClip.{interactionSoundClip, navigationSoundClip}

import javax.sound.sampled.Clip

/**
 * Represents the sound effect in the game.
 */
object SoundPlayer {

  private def playClip(clip: Clip): Unit = {
    clip.setFramePosition(0)
    clip.start()
  }

  private def loopClip(clip: Clip): Unit = {
    clip.setFramePosition(0)
    clip.loop(Clip.LOOP_CONTINUOUSLY)
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

  /**
   * Used to play music during the game story.
   */
  def playStoryMusicSound(): Unit = {
    loopClip(storyMusic)
  }

  /**
   * Used to play music during the game battle.
   */
  def playBattleMusicSound(): Unit = {
    loopClip(battleMusic)
  }

  /**
   * Used to play music during the main menu.
   */
  def playMenuMusicSound(): Unit = {
    loopClip(menuMusic)
  }

}
