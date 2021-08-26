package view

import view.util.scalaQuestSwingComponents.SqSwingBorderPanel

import java.awt._
import javax.swing.border.EmptyBorder
import javax.swing._

/**
 * The one and only Frame, for all the GUI's
 */
object Frame {

  private val GameTitle: String = "ScalaQuest"
  private val SquarePadding: Int = 10
  private val SquareScreenPercentage: Double = 0.8
  private val MinScreenSizePercentage: Double = 1.1

  val frame = new JFrame()
  private val squarePanel = new SquarePanel()
  private val box = new Box(BoxLayout.Y_AXIS)

  init()

  private var _currentJPanel: Option[JPanel] = None

  def setVisible(visible: Boolean): Unit = frame.setVisible(visible)

  def setPanel(jPanel: JPanel): Unit = {
    if (_currentJPanel.nonEmpty) squarePanel.remove(_currentJPanel.get)
    _currentJPanel = Some(jPanel)
    _currentJPanel.get.setBorder(new EmptyBorder(SquarePadding, SquarePadding, SquarePadding ,SquarePadding))
    squarePanel.add(jPanel, BorderLayout.CENTER)
    frame.repaint()
  }

  private def init(): Unit = {
    frame.setFocusable(true)
    frame.setTitle(GameTitle)
    frame.getContentPane.setBackground(Color.BLACK)
    box.setAlignmentX(Component.CENTER_ALIGNMENT)
    box.add(Box.createVerticalGlue())
    box.add(squarePanel)
    box.add(Box.createVerticalGlue())
    frame.add(box)
    frame.setMinimumSize(scaleDimension(getSquareDimension, MinScreenSizePercentage))
    frame.pack()
  }

  private def getSquareDimension: Dimension = {
    val screenSize = Toolkit.getDefaultToolkit.getScreenSize
    val side: Int = (Math.min(screenSize.height, screenSize.width) * SquareScreenPercentage).toInt
    new Dimension(side, side)
  }

  private def scaleDimension(dimension: Dimension, scale: Double): Dimension =
    new Dimension((dimension.width * scale).toInt, (dimension.height * scale).toInt)

  private class SquarePanel extends SqSwingBorderPanel {

    this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY))

    override def getMinimumSize: Dimension = getSquareDimension

    override def getMaximumSize: Dimension = getMinimumSize

    override def getPreferredSize: Dimension = getMinimumSize

  }

}
