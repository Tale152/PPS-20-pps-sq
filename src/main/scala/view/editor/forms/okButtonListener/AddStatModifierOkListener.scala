package view.editor.forms.okButtonListener

import controller.editor.EditorController
import model.characters.properties.stats.StatName.StatName
import model.characters.properties.stats.{StatModifier, StatName}
import view.editor.forms.AddStatModifier
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects._
import view.editor.forms.EditorConditionValues.ConditionDescriptions.{doesNotExists, mustBeSpecified}
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form

case class AddStatModifierOkListener(override val form: Form, override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  //match requires stable identifiers
  private val WisdomString: String = StatName.Wisdom.toString
  private val CharismaString: String = StatName.Charisma.toString
  private val StrengthString: String = StatName.Strength.toString
  private val DexterityString: String = StatName.Dexterity.toString
  private val IntelligenceString: String = StatName.Intelligence.toString
  private val ConstitutionString: String = StatName.Constitution.toString

  override def editorControllerAction(): Unit =
    controller.addStatModifierToNode(
      form.elements(AddStatModifier.SelectedNodeIndex).value.toInt,
      StatModifier(
        getSelectedStatName(form.elements(AddStatModifier.AffectedStatIndex).value),
        getStatModifierStrategy(
          form.elements(AddStatModifier.SelectedStrategyIndex).value,
          form.elements(AddStatModifier.ValueIndex).value.toInt
        )
      ),
      form.elements(AddStatModifier.DescriptionIndex).value
    )

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements(AddStatModifier.SelectedNodeIndex).value), mustBeSpecified(TheId)),
    (NonEmptyString(form.elements(AddStatModifier.DescriptionIndex).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(AddStatModifier.ValueIndex).value), mustBeSpecified(TheValue))
  )

  override def stateConditions: List[(Boolean, String)] = List(
    (controller.getStoryNode(form.elements(AddStatModifier.SelectedNodeIndex).value.toInt).isDefined,
      doesNotExists(TheStoryNode))
  )

  private def getSelectedStatName(statNameStr: String): StatName = statNameStr match {
    case WisdomString => StatName.Wisdom
    case CharismaString => StatName.Charisma
    case StrengthString => StatName.Strength
    case DexterityString => StatName.Dexterity
    case IntelligenceString => StatName.Intelligence
    case ConstitutionString => StatName.Constitution
  }

  private def getStatModifierStrategy(selectedStrategyStr: String, value: Int): Int => Int = selectedStrategyStr match {
    case AddStatModifier.IncrementOption => v => v + value
    case AddStatModifier.DecrementOption => v => v - value
  }
}
