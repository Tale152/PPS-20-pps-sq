package view.editor.okButtonListener.events.items

import controller.editor.EditorController
import model.characters.properties.stats.{DecrementOnApplyStatModifier, IncrementOnApplyStatModifier, StatModifier}
import model.characters.properties.stats.StatName._
import model.items.EquipItem
import model.nodes.ItemEvent
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.{TheDescription, TheName, TheNarrative}
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListenerStateless
import view.editor.okButtonListener.events.items.NewItemCategoryOkListener._
import view.editor.util.EquipItemTypeUtil.equipItemType
import view.editor.util.OperationStringUtil.{DecrementOption, IncrementOption}
import view.form.Form

case class NewEquipItemOkListener(override val form: Form,
                                  nodeId: Int,
                                  override val controller: EditorController)
  extends EditorOkFormButtonListenerStateless(form, controller) {

  override def editorControllerAction(): Unit = {

    def getOnApplyStatModifier(onApplyString: String, value: Int): Int => Int = onApplyString match {
      case IncrementOption => IncrementOnApplyStatModifier(value)
      case DecrementOption => DecrementOnApplyStatModifier(value)
    }

    controller.nodesControls.addEventToNode(nodeId, ItemEvent(
      form.elements(ItemRetrieveNarrativeIndex).value,
      EquipItem(
        form.elements(ItemNameIndex).value,
        form.elements(ItemDescriptionIndex).value,
        List(
          StatModifier(Charisma, getOnApplyStatModifier(
            form.elements(EquipEffectCharismaIndex).value, form.elements(EquipCharismaValueIndex).value.toInt)),
          StatModifier(Constitution, getOnApplyStatModifier(
            form.elements(EquipEffectConstitutionIndex).value, form.elements(EquipConstitutionValueIndex).value.toInt)),
          StatModifier(Dexterity, getOnApplyStatModifier(
            form.elements(EquipEffectDexterityIndex).value, form.elements(EquipDexterityValueIndex).value.toInt)),
          StatModifier(Intelligence, getOnApplyStatModifier(
            form.elements(EquipEffectIntelligenceIndex).value, form.elements(EquipIntelligenceValueIndex).value.toInt)),
          StatModifier(Strength, getOnApplyStatModifier(
            form.elements(EquipEffectStrengthIndex).value, form.elements(EquipStrengthValueIndex).value.toInt)),
          StatModifier(Wisdom, getOnApplyStatModifier(
            form.elements(EquipEffectWisdomIndex).value, form.elements(EquipWisdomValueIndex).value.toInt))
        ),
        equipItemType(form.elements(EquipTypeIndex).value)
      )
    ))
  }

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements(ItemNameIndex).value), mustBeSpecified(TheName)),
    (NonEmptyString(form.elements(ItemDescriptionIndex).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(ItemRetrieveNarrativeIndex).value), mustBeSpecified(TheNarrative))
  )

}
