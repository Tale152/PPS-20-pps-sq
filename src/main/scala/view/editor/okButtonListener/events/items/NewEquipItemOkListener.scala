package view.editor.okButtonListener.events.items

import controller.editor.EditorController
import model.characters.properties.stats.StatModifier
import model.characters.properties.stats.StatName._
import model.items.EquipItem
import model.nodes.ItemEvent
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.{TheDescription, TheName, TheNarrative}
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.editor.okButtonListener.events.items.NewItemCategoryOkListener._
import view.editor.util.EquipItemTypeUtil.getEquipItemType
import view.editor.util.OperationStringUtil.{DecrementOption, IncrementOption}
import view.editor.util.StatsNameStringUtil._
import view.form.Form

case class NewEquipItemOkListener(override val form: Form,
                                  nodeId: Int,
                                  override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit = {

    def getModifierStrategy(selectedStrategyStr: String, value: Int): Int => Int = selectedStrategyStr match {
      case IncrementOption => v => v + value
      case DecrementOption => v => v - value
    }

    controller.nodesControls.addEventToNode(nodeId, ItemEvent(
      form.elements(ItemRetrieveNarrativeIndex).value,
      EquipItem(
        form.elements(ItemNameIndex).value,
        form.elements(ItemDescriptionIndex).value,
        Set(
          StatModifier(Charisma, getModifierStrategy(
            form.elements(EquipEffectCharismaIndex).value, form.elements(EquipCharismaValueIndex).value.toInt)),
          StatModifier(Constitution, getModifierStrategy(
            form.elements(EquipEffectConstitutionIndex).value, form.elements(EquipConstitutionValueIndex).value.toInt)),
          StatModifier(Dexterity, getModifierStrategy(
            form.elements(EquipEffectDexterityIndex).value, form.elements(EquipDexterityValueIndex).value.toInt)),
          StatModifier(Intelligence, getModifierStrategy(
            form.elements(EquipEffectIntelligenceIndex).value, form.elements(EquipIntelligenceValueIndex).value.toInt)),
          StatModifier(Strength, getModifierStrategy(
            form.elements(EquipEffectStrengthIndex).value, form.elements(EquipStrengthValueIndex).value.toInt)),
          StatModifier(Wisdom, getModifierStrategy(
            form.elements(EquipEffectWisdomIndex).value, form.elements(EquipWisdomValueIndex).value.toInt))
        ),
        getEquipItemType(form.elements(EquipTypeIndex).value)
      )
    ))
  }

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements(ItemNameIndex).value), mustBeSpecified(TheName)),
    (NonEmptyString(form.elements(ItemDescriptionIndex).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(ItemRetrieveNarrativeIndex).value), mustBeSpecified(TheNarrative)),
    (NonEmptyString(form.elements(EquipCharismaValueIndex).value), mustBeSpecified(CharismaString)),
    (NonEmptyString(form.elements(EquipConstitutionValueIndex).value), mustBeSpecified(ConstitutionString)),
    (NonEmptyString(form.elements(EquipDexterityValueIndex).value), mustBeSpecified(DexterityString)),
    (NonEmptyString(form.elements(EquipIntelligenceValueIndex).value), mustBeSpecified(IntelligenceString)),
    (NonEmptyString(form.elements(EquipStrengthValueIndex).value), mustBeSpecified(StrengthString)),
    (NonEmptyString(form.elements(EquipWisdomValueIndex).value), mustBeSpecified(WisdomString))
  )

  override def stateConditions: List[(Boolean, String)] = List()
}
