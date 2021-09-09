package view.util.scalaQuestSwingComponents.dialog

import view.Frame.frame
import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingGridPanel, SqSwingLabel}

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.{JButton, JDialog, WindowConstants}

/**
 * Represents a custom dialog for ScalaQuest.
 *
 * @param titleText the title of the Dialog.
 * @param phrase    a description shown to the user.
 * @param buttons   a list of Buttons to let the user take decisions.
 */
case class SqSwingDialog(titleText: String, phrase: String, buttons: List[JButton], closable: Boolean = true)
  extends JDialog(frame) {
  val phrasePanel: SqSwingFlowPanel = new SqSwingFlowPanel {}
  val buttonsPanel: SqSwingGridPanel = new SqSwingGridPanel(0, buttons.length) {}
  phrasePanel.add(SqSwingLabel(phrase))
  this.setLayout(new BorderLayout())
  if (!closable) {
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)
  }

  buttons.foreach(b => {
    buttonsPanel.add(b)
    b.addActionListener((_: ActionEvent) => dispose())
  })
  this.add(phrasePanel, BorderLayout.NORTH)
  this.add(buttonsPanel, BorderLayout.CENTER)
  this.setTitle(titleText)
  this.pack()
  this.setLocationRelativeTo(frame)
  this.setModal(true)
  this.setVisible(true)
}
