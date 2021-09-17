package view.util.scalaQuestSwingComponents

import view.util.SoundPlayer
import java.awt.Color
import java.awt.event.{ActionEvent, ActionListener}

import javax.swing.JButton
import view.util.StringUtil.ButtonTextSize

/**
 * Represents a custom button for ScalaQuest.
 *
 * @param text          the text of the button.
 * @param action        the action associated to the click of the button.
 * @param buttonEnabled true if the button has to be enabled.
 */
case class SqSwingButton(text: String, action: ActionListener, buttonEnabled: Boolean = true) extends JButton {
  this.setText(text)
  this.changeTextColor(Color.WHITE)
  this.setContentAreaFilled(false)
  this.addActionListener(action)
  this.addActionListener((_: ActionEvent) => SoundPlayer.playInteractionSound())
  this.setEnabled(buttonEnabled)
  this.setFocusable(false)
  this.setFont(SqFont(bold = true, ButtonTextSize))

  def changeTextColor(color: Color): Unit = this.setForeground(color)
}
