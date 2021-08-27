package view.util.common

import view.Frame.frame
import view.util.scalaQuestSwingComponents.SqSwingButton.SqSwingButton
import view.util.scalaQuestSwingComponents.SqSwingGridBagPanel

import java.awt.{GridBagConstraints, Insets}
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform
import javax.swing.AbstractButton

case class VerticalButtons(buttons: Set[SqSwingButton]) extends SqSwingGridBagPanel {

  private val buttonHeight = 20
  private val verticalPadding = 5
  private val c = new GridBagConstraints
  private val STR_NEWLINE = "<br/>"
  private val fontRenderContext = new FontRenderContext(new AffineTransform, true, true)

  c.fill = GridBagConstraints.HORIZONTAL
  c.ipady = buttonHeight
  c.weightx = 0.0
  c.gridwidth = 3
  c.gridx = 0
  c.insets = new Insets(verticalPadding, 0, verticalPadding, 0)

  buttons.foreach(b => {
    b.setText(getWrappedText(b, b.getText))
    this.add(b, c)
  })

  private def getWrappedText(button: AbstractButton, str: String): String = {
    var newStr: String = str
    if (!str.contains(STR_NEWLINE) && (frame.getWidth - 5) <
      button.getFont.getStringBounds(str, fontRenderContext).getWidth.intValue
    ) {
      val strLength = (str.length / 3) * 2
      newStr = str.substring(0, strLength) + STR_NEWLINE + str.substring(strLength)
    }
    newStr
  }
}
