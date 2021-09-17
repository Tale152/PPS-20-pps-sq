package view.util.scalaQuestSwingComponents

import java.awt.Color

import javax.swing.{JLabel, SwingConstants}
import view.util.StringUtil.DefaultFontSize

/**
 * Represents a custom label for ScalaQuest.
 *
 * @param text      the text to show in the label.
 * @param color     the foreground color of the label.
 * @param labelSize the label text size.
 * @param bold      true if the label's text needs to be bold.
 * @param alignment specify the alignment of the label using a [[javax.swing.SwingConstants]].
 */
case class SqSwingLabel(text: String,
                        color: Color = Color.WHITE,
                        labelSize: Int = DefaultFontSize,
                        bold: Boolean = false,
                        alignment: Int = SwingConstants.HORIZONTAL) extends JLabel {
  this.setText(text)
  this.setForeground(color)
  this.setFont(SqFont(bold, labelSize))
  this.setHorizontalAlignment(alignment)
}
