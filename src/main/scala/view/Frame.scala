package view

import javax.swing.{JFrame, JPanel}

/**
 * The one and only Frame, for all the GUI's
 */
object Frame {

  val frame = new JFrame()
  var _currentJPanel: Option[JPanel] = None
  frame.setUndecorated(true)
  frame.setFocusable(true)
  frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH)
  //frame.setMinimumSize(new Dimension(450,400))

  def setVisible(visible: Boolean): Unit = frame.setVisible(visible)

  def setPanel(jPanel: JPanel): Unit = {
    if (_currentJPanel.nonEmpty) frame.remove(_currentJPanel.get)
    _currentJPanel = Some(jPanel)
    frame.add(jPanel)
    frame.repaint()
  }

}
