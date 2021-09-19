package view.util.common

import controller.util.SoundPlayer
import view.Frame
import view.Frame.frame
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.SqSwingGridBagPanel

import java.awt.event.{KeyEvent, KeyListener}
import java.awt.{Color, GridBagConstraints, Insets}
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform
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

  private var _selected = 0

  if(buttonsList.nonEmpty) {
    buttonsList(_selected).changeTextColor(Color.GREEN)
    Frame.frame.addKeyListener(new KeyListener {
      override def keyTyped(e: KeyEvent): Unit = { /*does nothing*/ }

      override def keyPressed(e: KeyEvent): Unit = {
        buttonsList(_selected).changeTextColor(Color.WHITE)
        e.getExtendedKeyCode match {
          case KeyEvent.VK_UP =>
            if (_selected - 1 == -1) {
              _selected = buttonsList.size - 1
            } else {
              _selected -= 1
            }
            SoundPlayer.playNavigationSound()
          case KeyEvent.VK_DOWN =>
            if (_selected + 1 == buttonsList.size){
              _selected = 0
            } else {
              _selected += 1
            }
            SoundPlayer.playNavigationSound()
          case KeyEvent.VK_ENTER =>
            buttonsList(_selected).doClick()
          case _ => /* does nothing, doesn't throw exception */
        }
        buttonsList(_selected).changeTextColor(Color.GREEN)
      }

      override def keyReleased(e: KeyEvent): Unit = {/*does nothing*/}
    })
  }

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
    import view.util.StringFormatUtil.FormatElements.NewLine
    var newStr: String = str
    if (!str.contains(NewLine) && (frame.getWidth - 5) <
      button.getFont.getStringBounds(str, fontRenderContext).getWidth.intValue
    ) {
      val strLength = (str.length / 3) * 2
      newStr = str.substring(0, strLength) + NewLine + str.substring(strLength)
    }
    newStr
  }
}
