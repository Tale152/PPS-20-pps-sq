package view.util.scalaQuestSwingComponents

import java.awt.Color
import java.awt.event.{ActionListener, MouseAdapter, MouseEvent}
import javax.swing.JButton

object SqSwingButton {

  val btnTextSize = 20

  class SqSwingButton(text: String, action: ActionListener, enabled: Boolean) extends JButton {
    this.setText(text)
    this.changeAppearance(Color.WHITE)
    this.setContentAreaFilled(false)
    this.addActionListener(action)
    this.setEnabled(enabled)
    this.setFocusPainted(false)
    this.setRolloverEnabled(false)
    this.addMouseListener(new MouseAdapter {
      override def mouseEntered(e: MouseEvent): Unit = changeAppearance(Color.GREEN)

      override def mouseExited(e: MouseEvent): Unit = changeAppearance(Color.WHITE)
    })
    this.setFocusable(false)
    this.setFont(SqFont(bold = true, btnTextSize))

    private def changeAppearance(color: Color): Unit = this.setForeground(color)
  }

  def apply(text: String, action: ActionListener): SqSwingButton = new SqSwingButton(text, action, true)

  def apply(text: String, action: ActionListener, enabled: Boolean): SqSwingButton =
    new SqSwingButton(text, action, enabled)
}
