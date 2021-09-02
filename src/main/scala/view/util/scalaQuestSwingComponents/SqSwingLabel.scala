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

  /**
   * Represents a custom label for ScalaQuest.
   *
   * @param text  the text to show in the label.
   * @param color the foreground color of the label.
   * @param size  the label text sixe.
   * @param bold  true if the label's text needs to be bold.
   */
  class SqSwingLabel(text: String, color: Color, size: Int, bold: Boolean) extends JLabel {
    this.setText(text)
    this.setForeground(color)
    this.setFont(SqFont(bold, size))
  }

  def apply(text: String, color: Color = DefaultColor, size: Int = LblTextSize, bold: Boolean = false): SqSwingLabel =
    new SqSwingLabel(text, color, size, bold)
}

object SqSwingCenteredLabel {

  /**
   * Represents a custom centered label for ScalaQuest.
   *
   * @param text  the text to show in the label.
   * @param color the foreground color of the label.
   * @param size  the label text sixe.
   * @param bold  true if the label's text needs to be bold.
   */
  class SqSwingCenteredLabel(text: String, color: Color, size: Int, bold: Boolean)
    extends SqSwingLabel(text, color, size, bold) {
    this.setHorizontalAlignment(SwingConstants.CENTER)
  }

  def apply(text: String, color: Color = DefaultColor, size: Int = LblTextSize,
            bold: Boolean = false): SqSwingCenteredLabel =
    new SqSwingCenteredLabel(text, color, size, bold)
}
