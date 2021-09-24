package view.util.scalaQuestSwingComponents

import controller.util.audio.SoundPlayer

import java.awt.Color
import java.awt.event.{ActionEvent, ActionListener, MouseAdapter, MouseEvent}
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

  private val thisButton = this
  this.addMouseListener(new MouseAdapter {
    override def mouseEntered(e: MouseEvent): Unit = {
      super.mouseEntered(e)
      SoundPlayer.playNavigationSound()
      thisButton.changeTextColor(Color.GREEN)
    }

    override def mouseExited(e: MouseEvent): Unit = {
      super.mouseExited(e)
      thisButton.changeTextColor(Color.WHITE)
    }
  })

  def changeTextColor(color: Color): Unit = this.setForeground(color)
}
