package controller.util

import controller.util.Resources.MusicClip.{battleMusic, menuMusic, storyMusic}
import controller.util.SoundPlayer.{playBattleMusicSound, playMenuMusicSound, playStoryMusicSound}

import javax.sound.sampled.Clip

object MusicManager {

  val musicFiles: List[Clip] = List(storyMusic, battleMusic, menuMusic)

  /**
   * Used to play music during story navigation.
   */
  def playStoryMusic(): Unit = {
    stopMusic()
    playStoryMusicSound()
  }

  /**
   * Used to play music during a battle.
   */
  def playBattleMusic(): Unit = {
    stopMusic()
    playBattleMusicSound()
  }

  /**
   * Used to play music during main menu navigation.
   */
  def playMenuMusic(): Unit = {
    stopMusic()
    playMenuMusicSound()
  }

  /**
   * Stops all possible sources of music.
   */
  def stopMusic(): Unit = {
    musicFiles.foreach(i => i.stop())
  }
}
