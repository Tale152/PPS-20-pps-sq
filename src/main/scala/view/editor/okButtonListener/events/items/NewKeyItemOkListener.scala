package view.editor.okButtonListener.events.items

import controller.editor.EditorController
import model.items.KeyItem
import model.nodes.ItemEvent
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.{TheDescription, TheName, TheNarrative}
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListenerStateless
import view.editor.okButtonListener.events.items.NewItemCategoryOkListener.{ItemDescriptionIndex, ItemNameIndex, ItemRetrieveNarrativeIndex}
import view.form.Form

case class NewKeyItemOkListener(override val form: Form,
                                nodeId: Int,
                                override val controller: EditorController)
  extends EditorOkFormButtonListenerStateless(form, controller) {

  override def editorControllerAction(): Unit = controller.nodesControls.addEventToNode(nodeId, ItemEvent(
    form.elements(ItemRetrieveNarrativeIndex).value,
    KeyItem(form.elements(ItemNameIndex).value, form.elements(ItemDescriptionIndex).value)
  ))

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements(ItemNameIndex).value), mustBeSpecified(TheName)),
    (NonEmptyString(form.elements(ItemDescriptionIndex).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(ItemRetrieveNarrativeIndex).value), mustBeSpecified(TheNarrative))
  )
}
