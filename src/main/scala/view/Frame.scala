package view

import controller.util.Resources.loadImage
import view.util.scalaQuestSwingComponents.SqSwingBorderPanel

import java.awt._
import javax.swing._
import javax.swing.border.EmptyBorder

/**
 * The one and only Frame, for all the GUI's.
 */
object Frame {

  private object FrameValues {
    val GameTitle: String = "ScalaQuest"
    val SquarePadding: Int = 10
    val SquareScreenPercentage: Double = 0.8
    val MinScreenSizePercentage: Double = 1.1
  }

  import view.Frame.FrameValues._

  val frame = new JFrame()
  private val masterPanel = new MasterPanel()
  private val squarePanel = new SquarePanel()
  private val box = new Box(BoxLayout.Y_AXIS)

  init()

  private var _currentJPanel: Option[JPanel] = None

  def setVisible(visible: Boolean): Unit = frame.setVisible(visible)

  def setPanel(jPanel: JPanel): Unit = {
    if (_currentJPanel.nonEmpty) squarePanel.remove(_currentJPanel.get)
    _currentJPanel = Some(jPanel)
    _currentJPanel.get.setBorder(new EmptyBorder(SquarePadding, SquarePadding, SquarePadding, SquarePadding))
    squarePanel.add(jPanel, BorderLayout.CENTER)
    frame.repaint()
  }

  private def init(): Unit = {
    frame.setIconImage(loadImage("/Icon.png"))
    frame.setBackground(Color.BLACK)
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setFocusable(true)
    frame.setTitle(GameTitle)
    frame.getContentPane.setBackground(Color.BLACK)
    frame.getContentPane.add(masterPanel)
    box.setAlignmentX(Component.CENTER_ALIGNMENT)
    box.add(Box.createVerticalGlue())
    box.add(squarePanel)
    box.add(Box.createVerticalGlue())
    masterPanel.add(box)
    frame.setMinimumSize(scaleDimension(squareDimension, MinScreenSizePercentage))
    frame.pack()
  }

  def squareDimension: Dimension = {
    val screenSize = Toolkit.getDefaultToolkit.getScreenSize
    val side: Int = (Math.min(screenSize.height, screenSize.width) * SquareScreenPercentage).toInt
    new Dimension(side, side)
  }

  private def scaleDimension(dimension: Dimension, scale: Double): Dimension =
    new Dimension((dimension.width * scale).toInt, (dimension.height * scale).toInt)

  private class SquarePanel extends SqSwingBorderPanel {

    this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))

    override def getMinimumSize: Dimension = squareDimension

    override def getMaximumSize: Dimension = getMinimumSize

    override def getPreferredSize: Dimension = getMinimumSize

  }

  private class MasterPanel extends SqSwingBorderPanel {

    this.setBackground(Color.BLACK)

    override def paintComponent(g: Graphics): Unit = {
      val bg = loadImage("/general_background.png")
      val g2d: Graphics2D = g.create().asInstanceOf[Graphics2D]
      var _y = 0
      var _x = 0
      while (_y < getHeight) {
        while (_x < getWidth) {
          g2d.drawImage(
            bg,
            _x,
            _y,
            (_: Image, _: Int, _: Int, _: Int, _: Int, _: Int) => true
          )
          _x = _x + bg.getWidth
        }
        _x = 0
        _y = _y + bg.getHeight
      }
      g2d.dispose()
    }
  }

}
