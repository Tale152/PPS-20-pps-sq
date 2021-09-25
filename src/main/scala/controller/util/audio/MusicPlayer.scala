package controller.util.audio

import controller.util.ResourceNames._
import controller.util.Resources.loadAudioClips

import javax.sound.sampled.Clip

object MusicPlayer {

  val storyMusicClips: Set[Clip] = loadAudioClips(storyMusicDirectoryPath())
  val battleMusicClips: Set[Clip] = loadAudioClips(battleMusicDirectoryPath())
  val menuMusicClips: Set[Clip] = loadAudioClips(menuMusicDirectoryPath())
  var currentlyPlayingStory: Option[Clip] = None
  var currentlyPlayingBattle: Option[Clip] = None
  var currentlyPlayingMenu: Option[Clip] = None

  /**
   * Used to play music during story navigation.
   */
  def playStoryMusic(): Unit = {
    if (currentlyPlayingStory.isEmpty) currentlyPlayingStory = storyMusicClips.randomClip
    playMusic(currentlyPlayingStory)
  }

  /**
   * Used to play music during a battle.
   */
  def playBattleMusic(): Unit = {
    if (currentlyPlayingBattle.isEmpty) currentlyPlayingBattle = battleMusicClips.randomClip
    playMusic(currentlyPlayingBattle)
  }

  /**
   * Used to play music during menu navigation.
   */
  def playMenuMusic(): Unit = {
    stopMusic()
    currentlyPlayingStory = None
    currentlyPlayingBattle = None
    currentlyPlayingMenu = None
    if (currentlyPlayingMenu.isEmpty) currentlyPlayingMenu = menuMusicClips.randomClip
    playMusic(currentlyPlayingMenu)
  }

  /**
   * Template method that exchange plays music.
   */
  def playMusic(currentlyPlayingClip: Option[Clip]): Unit = {
    stopMusic()
    if (currentlyPlayingClip.isDefined) {
      SoundPlayer.playClip(currentlyPlayingClip.get, loop = true)
    }
  }

  /**
   * Stops the current playing music.
   */
  def stopMusic(): Unit = {
    if (currentlyPlayingMenu.isDefined) {
      currentlyPlayingMenu.get.stop()
    }
    if (currentlyPlayingStory.isDefined) {
      currentlyPlayingStory.get.stop()
    }
    if (currentlyPlayingBattle.isDefined) {
      currentlyPlayingBattle.get.stop()
    }
  }

  /**
   * Set of all the music clips.
   *
   * @param clips the set of all the audio clips.
   */
  implicit class MusicSet(clips: Set[Clip]) {

    /**
     * Optionally takes a random clip in a set set of clips.
     *
     * @return the selected clip.
     */
    def randomClip: Option[Clip] = {
      import scala.util.Random
      if (clips.nonEmpty) {
        Option(clips.toVector(new Random().nextInt(clips.size)))
      } else {
        None
      }
    }
  }

}
