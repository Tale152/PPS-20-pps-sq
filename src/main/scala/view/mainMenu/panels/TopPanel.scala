package view.mainMenu.panels

import controller.util.audio.MusicPlayer.{playMenuMusic, stopMusic}
import controller.util.audio.SoundPlayer.isMute
import view.util.StringUtil.TitleSize
import view.util.scalaQuestSwingComponents.{SqSwingBorderPanel, SqSwingButton, SqSwingLabel}

import java.awt.BorderLayout
import javax.swing.SwingConstants

case class TopPanel() extends SqSwingBorderPanel {
  this.add(SqSwingLabel("Please select a story", labelSize = TitleSize,
    alignment = SwingConstants.CENTER), BorderLayout.CENTER)
  val muteButton: SqSwingButton = SqSwingButton(
    if (isMute) {
      "unMute"
    } else {
      "mute"
    }, _ => {
      isMute = !isMute
      if (!isMute) {
        muteButton.setText("mute")
        playMenuMusic()
      } else {
        muteButton.setText("unMute")
        stopMusic()
      }
    }
  )
  this.add(muteButton, BorderLayout.EAST)
}
