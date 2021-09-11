package view.util.scalaQuestSwingComponents.dialog

import view.util.scalaQuestSwingComponents.SqSwingButton

import java.awt.event.{ActionEvent, ActionListener}

/**
 * A [[view.util.scalaQuestSwingComponents.dialog.SqSwingDialog]] that is provided with two buttons, yes or no.
 */
case class SqYesNoSwingDialog(titleText: String,
                              phrase: String,
                              yesActionListener: ActionListener,
                              noActionListener: ActionListener = (_: ActionEvent) => {})
  extends SqAbstractSwingDialog(
    titleText,
    phrase,
    List(
      SqSwingButton("yes", yesActionListener),
      SqSwingButton("no", noActionListener)
    ))
