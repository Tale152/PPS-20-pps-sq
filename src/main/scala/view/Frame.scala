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

  def setVisible(visible: Boolean): Unit = {
    frame.setVisible(visible)
    frame.setMinimumSize(new Dimension(minimumFrameWidth, minimumFrameHeight))
    frame.pack()
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setTitle("ScalaQuest")
  }

  def setPanel(jpanel: JPanel): Unit = frame.add(jpanel)

}
