package view

import javax.swing.{JFrame, JPanel}

/**
 * The one and only Frame, for all the GUI's
 */
object Frame {

  val frame = new JFrame()

  def setVisible(visible: Boolean): Unit = frame.setVisible(visible)

  def setPanel(jpanel: JPanel): Unit = frame.add(jpanel)

}
