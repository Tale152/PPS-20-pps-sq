package view.util.scalaQuestSwingComponents

import view.util.SoundPlayer

import java.awt.Color
import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JButton

object SqSwingButton {

  val btnTextSize = 20

  class SqSwingButton(text: String, action: ActionListener, enabled: Boolean) extends JButton {
    this.setText(text)
    this.changeAppearance(Color.WHITE)
    this.setContentAreaFilled(false)
    this.addActionListener(action)
    this.addActionListener((_: ActionEvent) => SoundPlayer.playInteractionSound())
    this.setEnabled(enabled)
    this.setFocusable(false)
    this.setFont(SqFont(bold = true, btnTextSize))

    def changeAppearance(color: Color): Unit = this.setForeground(color)
  }

  def apply(text: String, action: ActionListener, enabled: Boolean = true): SqSwingButton =
    new SqSwingButton(text, action, enabled)
}
