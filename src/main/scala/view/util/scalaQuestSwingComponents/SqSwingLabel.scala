package view.util.scalaQuestSwingComponents

import view.util.scalaQuestSwingComponents.LabelValues.{DefaultColor, LblTextSize}
import view.util.scalaQuestSwingComponents.SqSwingLabel.SqSwingLabel

import java.awt.Color
import javax.swing.{JLabel, SwingConstants}

private object LabelValues {
  val LblTextSize: Int = 15
  val DefaultColor: Color = Color.WHITE
}

object SqSwingLabel {

  class SqSwingLabel(text: String, color: Color, size: Int) extends JLabel {
    this.setText(text)
    this.setForeground(color)
    this.setFont(SqFont(bold = false, size))
  }

  def apply(text: String, color: Color = DefaultColor, size: Int = LblTextSize): SqSwingLabel =
    new SqSwingLabel(text, color, size)
}

object SqSwingCenteredLabel {
  class SqSwingCenteredLabel(text: String, color: Color, size: Int) extends SqSwingLabel(text, color, size) {
    this.setHorizontalAlignment(SwingConstants.CENTER)
  }

  def apply(text: String, color: Color = DefaultColor, size: Int = LblTextSize): SqSwingCenteredLabel =
    new SqSwingCenteredLabel(text, color, size)
}
