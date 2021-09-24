package view.form

import controller.Controller
import view.util.StringFormatUtil.FormatElements.NewLine
import view.util.StringFormatUtil.formatted
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

import java.awt.event.{ActionEvent, ActionListener}

/**
 * Abstract Ok Button Listener designed to be used inside [[Form]].
 */
abstract class OkFormButtonListener(val form: Form, val controller: Controller)
  extends ActionListener {

  /**
   * Template method, the structure of the listener is defined.
   *
   * @param e the action event.
   */
  override def actionPerformed(e: ActionEvent): Unit = {
    if (areAllConditionSatisfied(inputConditions.map(c => c._1))) {
      if (areAllConditionSatisfied(stateConditions.map(c => c._1))) {
        performAction()
      } else {
        warningDialog(stateConditions)
      }
    } else {
      warningDialog(inputConditions)
    }
  }

  /**
   * Check if all the given conditions are true.
   *
   * @return true if all the conditions passed are satisfied.
   */
  val areAllConditionSatisfied: List[Boolean] => Boolean = l => l.forall(c => c)

  /**
   * @return a Dialog that print the errors ([[OkFormButtonListener#conditions()]]
   *         that are not satisfied).
   */
  def warningDialog(conditions: List[(Boolean, String)]): SqSwingDialog = {
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
   * Specify the conditions for the inputs and describe them.
   * If ALL are satisfied (&&) check for all the
   * [[OkFormButtonListener#editorStateCondition()]].
   *
   * @return a List containing inputs conditions with textual descriptions.
   */
  def inputConditions: List[(Boolean, String)]

  /**
   * Specify the conditions to check in the state of the model.
   * If ALL are satisfied (&&) call [[OkFormButtonListener#performAction()]].
   *
   * @return a List containing conditions that are state based with textual descriptions.
   */
  def stateConditions: List[(Boolean, String)]

}

/** @inheritdoc */
abstract class OkFormButtonListenerUnconditional(form: Form, controller: Controller)
  extends OkFormButtonListener(form, controller) {

  override def inputConditions: List[(Boolean, String)] = List()

  override def stateConditions: List[(Boolean, String)] = List()

}
