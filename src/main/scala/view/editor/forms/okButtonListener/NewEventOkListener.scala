package view.editor.forms.okButtonListener

import controller.editor.EditorController
import model.characters.properties.stats.StatName.StatName
import model.characters.properties.stats.{StatModifier, StatName}
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects.{TheDescription, TheValue}
import view.editor.forms.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.forms.NewEvent
import view.form.{Form, FormBuilder, OkFormButtonListener}

object NewEventOkListener {

  val IncrementOption: String = "Increment (+)"
  val DecrementOption: String = "Decrement (-)"

  private case class NewEventOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    private def showStatModifierForm(nodeId: Int): Unit = {
      val form: Form = FormBuilder()
        .addComboField("Which stat you want to affect?", List(
          StatName.Charisma.toString,
          StatName.Constitution.toString,
          StatName.Dexterity.toString,
          StatName.Intelligence.toString,
          StatName.Strength.toString,
          StatName.Wisdom.toString
        ))
        .addComboField("What you want to do with the selected stat?", List(IncrementOption, DecrementOption))
        .addIntegerField("Insert the value")
        .addTextAreaField("Insert a description for the stat modifier")
        .get(controller)
      form.setOkButtonListener(NewStatModifierOkListener(form, nodeId, controller))
      form.render()
    }

    override def performAction(): Unit = form.elements(1).value match {
      case NewEvent.StatModifierOption => showStatModifierForm(form.elements.head.value.toInt)
    }

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List()

  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener = NewEventOkListener(form, controller)
}

private case class NewStatModifierOkListener(override val form: Form,
                                     val nodeId: Int,
                                     override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  //match requires stable identifiers
  private val WisdomString: String = StatName.Wisdom.toString
  private val CharismaString: String = StatName.Charisma.toString
  private val StrengthString: String = StatName.Strength.toString
  private val DexterityString: String = StatName.Dexterity.toString
  private val IntelligenceString: String = StatName.Intelligence.toString
  private val ConstitutionString: String = StatName.Constitution.toString

  override def editorControllerAction(): Unit = {
    controller.addStatModifierToNode(
      nodeId,
      StatModifier(
        getSelectedStatName(form.elements.head.value),
        getStatModifierStrategy(
          form.elements(1).value,
          form.elements(2).value.toInt
        )
      ),
      form.elements(3).value
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