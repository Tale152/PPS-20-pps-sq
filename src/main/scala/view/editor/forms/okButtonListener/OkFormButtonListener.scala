package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.Form
import view.util.StringFormatUtil.FormatElements.NewLine
import view.util.StringFormatUtil.formatted
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

import java.awt.event.{ActionEvent, ActionListener}

/**
 * Abstract Ok Button Listener designed to be used inside [[view.editor.Form]].
 */
private[okButtonListener] abstract class OkFormButtonListener(val form: Form, val editorController: EditorController)
  extends ActionListener {

  /**
   * Template method, the structure of the listener is defined.
   *
   * @param e the action event.
   */
  override def actionPerformed(e: ActionEvent): Unit = {
    if (approvalCondition) {
      performAction()
    } else {
      warningDialog
    }
  }

  /**
   * The condition needed to call [[OkFormButtonListener#performAction()]].
   *
   * @return true if all the conditions specified in [[OkFormButtonListener#conditions()]] are satisfied.
   */
  def approvalCondition: Boolean = {
    conditions.forall(c => c._1)
  }

  /**
   * @return a Dialog that print the errors ([[OkFormButtonListener#conditions()]]
   *         that are not satisfied).
   */
  def warningDialog: SqSwingDialog = {
    SqSwingDialog("Illegal Input",
      formatted(conditions.filter(c => !c._1).map(c => "-" + c._2).mkString(NewLine)),
      List(SqSwingButton("OK", (_: ActionEvent) => {}))
    )
  }

  /**
   * The action to perform in case of success.
   *
   * @see [[OkFormButtonListener#approvalCondition()]]
   */
  def performAction(): Unit

  /**
   * Specify the conditions and describe them.
   * If ALL are satisfied (&&) call [[OkFormButtonListener#performAction()]].
   *
   * @return a List containing a condition and it's textual description.
   */
  def conditions: List[(Boolean, String)]

}


