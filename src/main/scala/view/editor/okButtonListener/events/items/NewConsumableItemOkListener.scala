package view.editor.okButtonListener.events.items

import controller.editor.EditorController
import model.characters.Character
import model.items.ConsumableItem
import model.nodes.ItemEvent
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects._
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.editor.util.OperationStringUtil.{DecrementOption, IncrementOption}
import view.form.Form

case class NewConsumableItemOkListener(override val form: Form,
                                       nodeId: Int,
                                       override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit = {

    def getConsumableStrategy(selectedStrategyStr: String, value: Int): Character => Unit = selectedStrategyStr match {
      case IncrementOption => c => c.properties.health.currentPS = c.properties.health.currentPS + value
      case DecrementOption => c => c.properties.health.currentPS = c.properties.health.currentPS - value
    }

    controller.addEventToNode(nodeId, ItemEvent(
      form.elements(4).value,
      ConsumableItem(
        form.elements.head.value,
        form.elements(1).value,
        getConsumableStrategy(form.elements(2).value, form.elements(3).value.toInt))
    ))
  }

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements.head.value), mustBeSpecified(TheName)),
    (NonEmptyString(form.elements(1).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(3).value), mustBeSpecified(TheValue)),
    (NonEmptyString(form.elements(4).value), mustBeSpecified(TheNarrative))
  )

  override def stateConditions: List[(Boolean, String)] = List()
}
