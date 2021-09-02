package view.util.scalaQuestSwingComponents

import view.util.SoundPlayer

import java.awt.Color
import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JButton

case class SqSwingButton(text: String, action: ActionListener, buttonEnabled: Boolean = true) extends JButton {
    private val btnTextSize = 20
    this.setText(text)
    this.changeAppearance(Color.WHITE)
    this.setContentAreaFilled(false)
    this.addActionListener(action)
    this.addActionListener((_: ActionEvent) => SoundPlayer.playInteractionSound())
    this.setEnabled(buttonEnabled)
    this.setFocusable(false)
    this.setFont(SqFont(bold = true, btnTextSize))

    def changeAppearance(color: Color): Unit = this.setForeground(color)
}

