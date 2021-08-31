package view

import view.util.scalaQuestSwingComponents.SqSwingBorderPanel

import java.awt._
import java.io.{BufferedInputStream, File, InputStream}
import java.nio.file.{Files, Paths}
import javax.imageio.ImageIO
import javax.swing._
import javax.swing.border.EmptyBorder

/**
 * The one and only Frame, for all the GUI's
 */
object Frame {

  private val GameTitle: String = "ScalaQuest"
  private val SquarePadding: Int = 10
  private val SquareScreenPercentage: Double = 0.8
  private val MinScreenSizePercentage: Double = 1.1

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
    frame.setMinimumSize(scaleDimension(getSquareDimension, MinScreenSizePercentage))
    frame.pack()
  }

  def loadFont(path: String): Font = {
    try {
      val is: InputStream = new BufferedInputStream(
        Files.newInputStream(Paths.get(path)))
      val myFont = Font.createFont(Font.TRUETYPE_FONT, is)
      myFont.deriveFont(Font.PLAIN)
    } catch {
      case _: Exception =>
        println("Error on loading external Font, loading default one..")
        Font.getFont("Arial")
    }
  }

  def getSquareDimension: Dimension = {
    val screenSize = Toolkit.getDefaultToolkit.getScreenSize
    val side: Int = (Math.min(screenSize.height, screenSize.width) * SquareScreenPercentage).toInt
    new Dimension(side, side)
  }

  private def scaleDimension(dimension: Dimension, scale: Double): Dimension =
    new Dimension((dimension.width * scale).toInt, (dimension.height * scale).toInt)

  private class SquarePanel extends SqSwingBorderPanel {

    this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3))

    override def getMinimumSize: Dimension = getSquareDimension

    override def getMaximumSize: Dimension = getMinimumSize

    override def getPreferredSize: Dimension = getMinimumSize

  }

  private class MasterPanel extends SqSwingBorderPanel {

    this.setBackground(Color.BLACK)

    override def paintComponent(g: Graphics): Unit = {
      val bg = ImageIO.read(new File(getClass.getResource("/general_background.png").getPath))
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
