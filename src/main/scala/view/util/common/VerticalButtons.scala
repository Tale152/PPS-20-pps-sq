package view.util.common

import view.Frame
import view.Frame.frame
import view.util.SoundPlayer
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.SqSwingGridBagPanel

import java.awt.event.{KeyEvent, KeyListener}
import java.awt.{Color, GridBagConstraints, Insets}
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform
import javax.swing.AbstractButton

case class VerticalButtons(buttonsList: List[SqSwingButton]) extends SqSwingGridBagPanel {

  private val buttonHeight = 20
  private val verticalPadding = 5
  private val c = new GridBagConstraints
  private val STR_NEWLINE = "<br/>"
  private val fontRenderContext = new FontRenderContext(new AffineTransform, true, true)

  private var _selected = 0

  if(buttonsList.nonEmpty) {
    buttonsList(_selected).changeAppearance(Color.GREEN)
    Frame.frame.addKeyListener(new KeyListener {
      override def keyTyped(e: KeyEvent): Unit = { /*does nothing*/ }

      override def keyPressed(e: KeyEvent): Unit = {
        buttonsList(_selected).changeAppearance(Color.WHITE)
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
        buttonsList(_selected).changeAppearance(Color.GREEN)
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
