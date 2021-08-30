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

  class SqSwingLabel(text: String, color: Color, size: Int, bold: Boolean) extends JLabel {
    this.setText(text)
    this.setForeground(color)
    this.setFont(SqFont(bold, size))
  }

  def apply(text: String, color: Color = DefaultColor, size: Int = LblTextSize, bold: Boolean = false): SqSwingLabel =
    new SqSwingLabel(text, color, size, bold)
}

object SqSwingCenteredLabel {
  class SqSwingCenteredLabel(text: String, color: Color, size: Int, bold: Boolean)
    extends SqSwingLabel(text, color, size, bold) {
    this.setHorizontalAlignment(SwingConstants.CENTER)
  }

  def apply(text: String, color: Color = DefaultColor, size: Int = LblTextSize,
            bold: Boolean = false): SqSwingCenteredLabel =
    new SqSwingCenteredLabel(text, color, size, bold)
}
