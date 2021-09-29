package view.util.common

import controller.util.StringUtil.StringFormatUtil.FormatElements.SwingNewLine
import view.Frame.frame
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingGridBagPanel}

import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform
import java.awt.{GridBagConstraints, Insets}
import javax.swing.AbstractButton

/**
 * Panel that renders a list of buttons in a vertical style.
 * @param buttonsList the list of buttons that is needed to be render vertically.
 */
case class VerticalButtons(buttonsList: List[SqSwingButton]) extends SqSwingGridBagPanel {

  private val buttonHeight = 20
  private val verticalPadding = 5
  private val c = new GridBagConstraints
  private val fontRenderContext = new FontRenderContext(new AffineTransform, true, true)

  c.fill = GridBagConstraints.HORIZONTAL
  c.ipady = buttonHeight
  c.weightx = 0.0
  c.gridwidth = 3
  c.gridx = 0
  c.insets = new Insets(verticalPadding, 0, verticalPadding, 0)

  buttonsList.foreach(b => {
    b.setText(getWrappedText(b, b.getText))
    this.add(b, c)
  })

  private def getWrappedText(button: AbstractButton, str: String): String = {
    var newStr: String = str
    if (!str.contains(SwingNewLine) && (frame.getWidth - 5) <
      button.getFont.getStringBounds(str, fontRenderContext).getWidth.intValue
    ) {
      val strLength = (str.length / 3) * 2
      newStr = str.substring(0, strLength) + SwingNewLine + str.substring(strLength)
    }
    newStr
  }
}
