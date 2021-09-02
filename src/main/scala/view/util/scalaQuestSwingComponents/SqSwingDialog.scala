package view.util.scalaQuestSwingComponents

import view.Frame.frame
import view.util.scalaQuestSwingComponents.SqSwingButton.SqSwingButton

import java.awt.BorderLayout
import java.awt.event.{ActionEvent, ActionListener}
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

  object YesNoSqSwingDialog {
    private class YesNoSqSwingDialogImpl(override val titleText: String, override val phrase: String,
                                         yesActionEvent: ActionListener, noActionEvent: ActionListener)
      extends SqSwingDialog(titleText, phrase, yesNoButtonList(yesActionEvent, noActionEvent))

    private def yesNoButtonList(yesActionListener: ActionListener,
                                noActionListener: ActionListener): List[SqSwingButton] = {
      List(
        new SqSwingButton("yes", yesActionListener, true),
        new SqSwingButton("no", noActionListener, true)
      )
    }

    def apply(titleText: String,
              phrase: String,
              yesActionListener: ActionListener,
              noActionListener: ActionListener = {(_: ActionEvent) => }
             ): SqSwingDialog =
      new YesNoSqSwingDialogImpl(titleText, phrase, yesActionListener, noActionListener)
  }



}
