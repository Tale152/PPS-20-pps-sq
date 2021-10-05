package view.util.scalaQuestSwingComponents.dialog

import view.Frame
import view.Frame.frame
import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingGridPanel, SqSwingTextArea}

import java.awt.{BorderLayout, Dimension}
import java.awt.event.ActionEvent
import javax.swing.{BoxLayout, JButton, JDialog, WindowConstants}

/**
 * Represents a custom dialog for ScalaQuest.
 *
 * @param titleText the title of the Dialog.
 * @param phrase    a description shown to the user.
 * @param buttons   a list of Buttons to let the user take decisions.
 */
abstract class SqAbstractSwingDialog(titleText: String,
                                     phrase: String,
                                     buttons: List[JButton],
                                     closable: Boolean = true) extends JDialog(frame) {
  val buttonsPanel: SqSwingGridPanel = new SqSwingGridPanel(0, buttons.length) {}

  this.setLayout(new BorderLayout())
  if (!closable) {
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)
  }

  buttons.foreach(b => {
    buttonsPanel.add(b)
    b.addActionListener((_: ActionEvent) => dispose())
  })
  this.add(MessagePanel(phrase), BorderLayout.NORTH)
  this.add(buttonsPanel, BorderLayout.CENTER)
  this.setTitle(titleText)
  this.pack()
  this.setLocationRelativeTo(frame)
  this.setModal(true)
  this.setVisible(true)

  override def getMinimumSize: Dimension =
    new Dimension(Frame.squareDimension.getWidth.toInt, (Frame.squareDimension.getHeight / 3).toInt)

  override def getMaximumSize: Dimension = getMinimumSize

  override def getPreferredSize: Dimension = getMinimumSize

}


private case class MessagePanel(message: String) extends SqSwingBoxPanel(BoxLayout.Y_AXIS) {
  this.add(SqSwingTextArea(message))

  override def getMinimumSize: Dimension =
    new Dimension(Frame.squareDimension.getWidth.toInt, (Frame.squareDimension.getHeight / 4.5).toInt)

  override def getMaximumSize: Dimension = getMinimumSize

  override def getPreferredSize: Dimension = getMinimumSize

}