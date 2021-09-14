package view.editor.forms.okButtonListener.events.statModifiers

import controller.editor.EditorController
import model.characters.properties.stats.{StatModifier, StatName}
import model.characters.properties.stats.StatName._
import model.nodes.StatEvent
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects.{TheDescription, TheValue}
import view.editor.forms.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.forms.okButtonListener.EditorOkFormButtonListener
import view.editor.forms.okButtonListener.events.NewEventOkListener
import view.form.Form

case class NewStatModifierOkListener(override val form: Form,
                                             nodeId: Int,
                                             override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  //match requires stable identifiers
  private val WisdomString: String = Wisdom.toString
  private val CharismaString: String = Charisma.toString
  private val StrengthString: String = Strength.toString
  private val DexterityString: String = Dexterity.toString
  private val IntelligenceString: String = Intelligence.toString
  private val ConstitutionString: String = Constitution.toString

  override def editorControllerAction(): Unit = {
    controller.addEventToNode(
      nodeId,
      StatEvent(form.elements(3).value,
        StatModifier(
          getSelectedStatName(form.elements.head.value),
          getStatModifierStrategy(form.elements(1).value, form.elements(2).value.toInt)
        )
      )
    )
  }

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements(3).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(2).value), mustBeSpecified(TheValue))
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
    case NewEventOkListener.IncrementOption => v => v + value
    case NewEventOkListener.DecrementOption => v => v - value
  }

}
