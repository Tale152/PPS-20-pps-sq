package view.util.scalaQuestSwingComponents

import java.awt.Color
import javax.swing.JLabel

object SqSwingLabel {

  class SqSwingLabel(text: String, color: Color) extends JLabel{
    this.setText(text)
    this.setForeground(color)
  }

  def apply(text: String): SqSwingLabel = new SqSwingLabel(text, Color.WHITE)

  def apply(text: String, color: Color): SqSwingLabel = new SqSwingLabel(text, color)
}

