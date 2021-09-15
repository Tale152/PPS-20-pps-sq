package view.editor.okButtonListener.events.items

import controller.editor.EditorController
import model.items.KeyItem
import model.nodes.ItemEvent
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.{TheDescription, TheName, TheNarrative}
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.form.Form

case class NewKeyItemOkListener(override val form: Form,
                                nodeId: Int,
                                override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit = controller.addEventToNode(nodeId, ItemEvent(
    form.elements(2).value,
    KeyItem(form.elements.head.value, form.elements(1).value)
  ))

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements.head.value), mustBeSpecified(TheName)),
    (NonEmptyString(form.elements(1).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(2).value), mustBeSpecified(TheNarrative))
  )

  override def stateConditions: List[(Boolean, String)] = List()
}
