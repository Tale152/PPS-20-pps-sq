package view.util.scalaQuestSwingComponents

import java.awt.Color
import javax.swing.{JLabel, SwingConstants}

case class SqSwingLabel(text: String,
                        color: Color = Color.WHITE,
                        labelSize: Int = {val size = 15; size},
                        bold: Boolean = false,
                        alignment: Int = SwingConstants.HORIZONTAL) extends JLabel {
    this.setText(text)
    this.setForeground(color)
    this.setFont(SqFont(bold, labelSize))
    this.setHorizontalAlignment(alignment)
  }






