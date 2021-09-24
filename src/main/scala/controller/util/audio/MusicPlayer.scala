package controller.util.audio

import controller.util.Resources.ResourceName._
import controller.util.Resources.loadAudioClips

import javax.sound.sampled.Clip

object MusicPlayer {

  val storyMusicClips: Set[Clip] = loadAudioClips(storyMusicDirectoryPath())
  val battleMusicClips: Set[Clip] = loadAudioClips(battleMusicDirectoryPath())
  val menuMusicClips: Set[Clip] = loadAudioClips(menuMusicDirectoryPath())
  None

  /**
   * Used to play music during story navigation.
   */
  def playStoryMusic(): Unit = {
    //playMusic(storyMusicClips.randomClip)
  }

  /**
   * Used to play music during a battle.
   */
  def playBattleMusic(): Unit = {
    //playMusic(battleMusicClips.randomClip)
  }

  /**
   * Used to play music during menu navigation.
   */
  def playMenuMusic(): Unit = {
    //playMusic(menuMusicClips.randomClip)
  }

  /**
   * Template method that exchange plays music.
   */
  def playMusic(clip: Option[Clip]): Unit = {
    /*stopMusic()
    currentlyPlaying = clip
    if (clip.isDefined) {
      SoundPlayer.playClip(currentlyPlaying.get, loop = true)
    }*/
  }

  /**
   * Stops the current playing music.
   */
  def stopMusic(): Unit = {
    /*if (currentlyPlaying.isDefined) {
      currentlyPlaying.get.stop()
    }*/
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
      /*import scala.util.Random
      if (clips.nonEmpty) {
        Option(clips.toVector(new Random().nextInt(clips.size)))
      } else {
        None
      }*/
      None
    }
  }

}
