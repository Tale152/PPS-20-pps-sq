package view.util.scalaQuestSwingComponents.dialog

import view.util.scalaQuestSwingComponents.SqSwingButton

import java.awt.event.{ActionEvent, ActionListener}

/**
 * A [[view.util.scalaQuestSwingComponents.dialog.SqSwingDialog]] that is provided with two buttons, yes or no.
 */
object SqYesNoSwingDialog {
  private class YesNoSqSwingDialogImpl(override val titleText: String, override val phrase: String,
                                       yesActionEvent: ActionListener, noActionEvent: ActionListener)
    extends SqSwingDialog(titleText, phrase, yesNoButtonList(yesActionEvent, noActionEvent))

  private def yesNoButtonList(yesActionListener: ActionListener,
                              noActionListener: ActionListener): List[SqSwingButton] = {
    List(
      SqSwingButton("yes", yesActionListener),
      SqSwingButton("no", noActionListener)
    )
  }

  def apply(titleText: String,
            phrase: String,
            yesActionListener: ActionListener,
            noActionListener: ActionListener = {(_: ActionEvent) => }
           ): SqSwingDialog =
    new YesNoSqSwingDialogImpl(titleText, phrase, yesActionListener, noActionListener)
}