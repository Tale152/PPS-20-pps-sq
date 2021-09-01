package view.util.scalaQuestSwingComponents

import controller.ApplicationController.loadStoryWithProgress
import view.Frame.frame

import java.awt.BorderLayout
import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.{JButton, JDialog}

object SqSwingDialog {

  case class SqSwingDialog(phrase: String, buttons: List[JButton]) extends JDialog(frame) {
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
    this.pack()
    this.setVisible(true)
    this.setLocationRelativeTo(frame)
  }

}
