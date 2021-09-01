package view.util.scalaQuestSwingComponents

import view.Frame.frame

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.{JButton, JDialog}

object SqSwingDialog {

  case class SqSwingDialog(titleText: String, phrase: String, buttons: List[JButton]) extends JDialog(frame) {
    val phrasePanel: SqSwingFlowPanel = new SqSwingFlowPanel {}
    val buttonsPanel: SqSwingGridPanel = new SqSwingGridPanel(0, buttons.length) {}
    phrasePanel.add(SqSwingLabel(phrase))
    this.setLayout(new BorderLayout())

    buttons.foreach(b => {
      buttonsPanel.add(b)
      b.addActionListener((_: ActionEvent) => dispose())
    })
    this.add(phrasePanel, BorderLayout.NORTH)
    this.add(buttonsPanel, BorderLayout.CENTER)
    this.setTitle(titleText)
    this.pack()
    this.setVisible(true)
    this.setLocationRelativeTo(frame)
  }

}
