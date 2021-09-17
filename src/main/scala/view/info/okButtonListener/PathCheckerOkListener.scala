package view.info.okButtonListener

import controller.InfoController
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.{TheEndingId, TheStartingId}
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form

case class PathCheckerOkListener(override val form: Form, override val controller: InfoController)
  extends InfoOkFormButtonListener(form, controller) {

  override def performAction(): Unit = {}

  override def inputConditions: List[(Boolean, String)] =
    List(
      (NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)),
      (NonEmptyString(form.elements(1).value), mustBeSpecified(TheEndingId))
    )

  override def stateConditions: List[(Boolean, String)] = List()
}
