package view.editor.okButtonListener.events.statModifiers

import controller.editor.EditorController
import model.characters.properties.stats.StatName._
import model.characters.properties.stats.{StatModifier, StatName}
import model.nodes.StatEvent
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.{TheDescription, TheValue}
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.editor.okButtonListener.events.NewEventOkListener._
import view.editor.util.OperationStringUtil.{DecrementOption, IncrementOption}
import view.editor.util.StatsNameStringUtil._
import view.form.Form

case class NewStatModifierOkListener(override val form: Form,
                                             nodeId: Int,
                                             override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit = {
    controller.addEventToNode(
      nodeId,
      StatEvent(form.elements(StatModifierDescriptionIndex).value,
        StatModifier(
          getSelectedStatName(form.elements(StatModifierFormStatIndex).value),
          getStatModifierStrategy(
            form.elements(StatModifierIncDecIndex).value,
            form.elements(StatModifierValueIndex).value.toInt
          )
        )
      )
    )
  }

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements(StatModifierDescriptionIndex).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(StatModifierValueIndex).value), mustBeSpecified(TheValue))
  )

  override def stateConditions: List[(Boolean, String)] = List()

  private def getSelectedStatName(statNameStr: String): StatName = statNameStr match {
    case WisdomString => StatName.Wisdom
    case CharismaString => StatName.Charisma
    case StrengthString => StatName.Strength
    case DexterityString => StatName.Dexterity
    case IntelligenceString => StatName.Intelligence
    case ConstitutionString => StatName.Constitution
  }

  private def getStatModifierStrategy(selectedStrategyStr: String, value: Int): Int => Int = selectedStrategyStr match {
    case IncrementOption => v => v + value
    case DecrementOption => v => v - value
  }

}
