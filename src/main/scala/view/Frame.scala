package view

import java.awt.Dimension
import javax.swing.{JFrame, JPanel, WindowConstants}

/**
 * The one and only Frame, for all the GUI's
 */
object Frame {

  val minimumFrameWidth = 500
  val minimumFrameHeight = 300

  val frame = new JFrame()
  var _currentJPanel: Option[JPanel] = None

  def setVisible(visible: Boolean): Unit = {
    frame.setVisible(visible)
    frame.setMinimumSize(new Dimension(minimumFrameWidth, minimumFrameHeight))
    frame.pack()
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setTitle("ScalaQuest")
  }

  def setPanel(jPanel: JPanel): Unit = {
    if(_currentJPanel.nonEmpty) frame.remove(_currentJPanel.get)
    _currentJPanel = Some(jPanel)
    frame.add(jPanel)
  }

}