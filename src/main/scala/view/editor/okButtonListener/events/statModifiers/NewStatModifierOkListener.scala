package view.editor.okButtonListener.events.statModifiers

import controller.editor.EditorController
import model.characters.properties.stats.{
  DecrementOnApplyStatModifier,
  IncrementOnApplyStatModifier,
  StatModifier,
  StatName
}
import model.characters.properties.stats.StatName._
import model.nodes.StatEvent
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.TheDescription
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListenerStateless
import view.editor.okButtonListener.events.NewEventOkListener._
import view.editor.util.OperationStringUtil.{DecrementOption, IncrementOption}
import view.editor.util.StatsNameStringUtil._
import view.form.Form

case class NewStatModifierOkListener(override val form: Form,
                                     nodeId: Int,
                                     override val controller: EditorController)
  extends EditorOkFormButtonListenerStateless(form, controller) {

  override def editorControllerAction(): Unit = {
    controller.nodesControls.addEventToNode(
      nodeId,
      StatEvent(form.elements(StatModifierDescriptionIndex).value,
        StatModifier(
          selectedStatName(form.elements(StatModifierFormStatIndex).value),
          onApplyStatModifier(
            form.elements(StatModifierIncDecIndex).value,
            form.elements(StatModifierValueIndex).value.toInt
          )
        )
      )
    )
  }

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements(StatModifierDescriptionIndex).value), mustBeSpecified(TheDescription))
  )

  private def selectedStatName(statNameStr: String): StatName = statNameStr match {
    case WisdomString => StatName.Wisdom
    case CharismaString => StatName.Charisma
    case StrengthString => StatName.Strength
    case DexterityString => StatName.Dexterity
    case IntelligenceString => StatName.Intelligence
    case ConstitutionString => StatName.Constitution
  }

  private def onApplyStatModifier(onApplyString: String, value: Int): Int => Int = onApplyString match {
    case IncrementOption => IncrementOnApplyStatModifier(value)
    case DecrementOption => DecrementOnApplyStatModifier(value)
  }

}
