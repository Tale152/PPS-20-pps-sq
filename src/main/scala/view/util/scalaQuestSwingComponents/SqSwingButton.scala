package view.util.scalaQuestSwingComponents

import java.awt.Font
import java.awt.event.ActionListener
import javax.swing.JButton

object SqSwingButton {

  val fontSize = 20

  class SqSwingButton(text: String, action: ActionListener, enabled: Boolean) extends JButton {
    this.setText(text)
    this.addActionListener(action)
    this.setEnabled(enabled)
    this.setFont(new Font("Arial", Font.PLAIN, fontSize))
  }

  def apply(text: String, action: ActionListener): SqSwingButton = new SqSwingButton(text, action, true)

  def apply(text: String, action: ActionListener, enabled: Boolean): SqSwingButton =
    new SqSwingButton(text, action, enabled)
}
