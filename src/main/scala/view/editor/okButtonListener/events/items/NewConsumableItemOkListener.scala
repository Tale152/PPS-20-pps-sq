package view.editor.okButtonListener.events.items

import controller.editor.EditorController
import model.characters.Character
import model.items.{ConsumableItem, DecrementHealthOnConsume, IncrementHealthOnConsume}
import model.nodes.ItemEvent
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects._
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListenerStateless
import view.editor.okButtonListener.events.items.NewItemCategoryOkListener._
import view.editor.util.OperationStringUtil.{DecrementOption, IncrementOption}
import view.form.Form

case class NewConsumableItemOkListener(override val form: Form,
                                       nodeId: Int,
                                       override val controller: EditorController)
  extends EditorOkFormButtonListenerStateless(form, controller) {

  override def editorControllerAction(): Unit = {

    def onConsume(onConsumeString: String, value: Int): Character => Unit = onConsumeString match {
      case IncrementOption => IncrementHealthOnConsume(value)
      case DecrementOption => DecrementHealthOnConsume(value)
    }

    controller.nodesControls.addEventToNode(nodeId, ItemEvent(
      form.elements(ItemRetrieveNarrativeIndex).value,
      ConsumableItem(
        form.elements(ItemNameIndex).value,
        form.elements(ItemDescriptionIndex).value,
        onConsume(
          form.elements(ConsumableEffectHealthIndex).value,
          form.elements(consumableHealthValueIndex).value.toInt)
      )
    ))
  }

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements(ItemNameIndex).value), mustBeSpecified(TheName)),
    (NonEmptyString(form.elements(ItemDescriptionIndex).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(ItemRetrieveNarrativeIndex).value), mustBeSpecified(TheNarrative))
  )

}
