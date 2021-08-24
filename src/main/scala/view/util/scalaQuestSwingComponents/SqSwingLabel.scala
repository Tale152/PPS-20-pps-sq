package view.util.scalaQuestSwingComponents

import view.util.scalaQuestSwingComponents.SqSwingLabel.SqSwingLabel

import java.awt.Color
import javax.swing.{JLabel, SwingConstants}

object SqSwingLabel {

  val lblTextSize = 15

  class SqSwingLabel(text: String, color: Color) extends JLabel {
    this.setText(text)
    this.setForeground(color)
    this.setFont(SqFont(lblTextSize))
  }

  def apply(text: String): SqSwingLabel = new SqSwingLabel(text, Color.WHITE)

  def apply(text: String, color: Color): SqSwingLabel = new SqSwingLabel(text, color)
}

object SqSwingCenteredLabel {
  class SqSwingCenteredLabel(text: String, color: Color) extends SqSwingLabel(text, color) {
    this.setHorizontalAlignment(SwingConstants.CENTER)
  }

  def apply(text: String): SqSwingCenteredLabel = new SqSwingCenteredLabel(text, Color.WHITE)

  def apply(text: String, color: Color): SqSwingCenteredLabel = new SqSwingCenteredLabel(text, color)
}
