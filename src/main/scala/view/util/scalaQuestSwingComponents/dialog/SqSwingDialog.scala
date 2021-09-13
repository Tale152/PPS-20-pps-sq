package view.util.scalaQuestSwingComponents.dialog

import javax.swing.JButton

/**
 * Represents a custom dialog for ScalaQuest. This class is the most basic Dialog.
 *
 * @param titleText the title of the Dialog.
 * @param phrase    a description shown to the user.
 * @param buttons   a list of Buttons to let the user take decisions.
 */
case class SqSwingDialog(titleText: String, phrase: String, buttons: List[JButton], closable: Boolean = true)
  extends SqAbstractSwingDialog (titleText, phrase, buttons, closable)