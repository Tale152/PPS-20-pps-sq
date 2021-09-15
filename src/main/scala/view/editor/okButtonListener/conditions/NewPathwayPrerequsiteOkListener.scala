package view.editor.okButtonListener.conditions

import controller.editor.EditorController
import model.characters.properties.stats.StatName._
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.TheId
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.okButtonListener.conditions.NewPathwayPrerequsiteOkListener.{KeyItemOption, StatValueOption}
import view.form.{Form, FormBuilder, OkFormButtonListener}

object NewPathwayPrerequsiteOkListener {

  val StatValueOption: String = "Stat value"
  val KeyItemOption: String = "Key item"

  case class NewPathwayConditionOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    override def performAction(): Unit = {
      val nextForm: Form = FormBuilder()
        .addComboField(
          "Select the destination node of the pathway (id)",
          controller.getStoryNode(form.elements.head.value.toInt).get
            .mutablePathways.filter(p => p.prerequisite.isEmpty)
            .map(p => p.destinationNode.id.toString).toSeq.toList
        )
        .addComboField("Prerequisite on", List(StatValueOption, KeyItemOption))
        .get(controller)
      nextForm.setOkButtonListener(
        NewPathwayPrerequisiteNextFormOkListener(nextForm, controller, form.elements.head.value.toInt)
      )
      nextForm.render()
    }

    override def inputConditions: List[(Boolean, String)] =
      List((form.elements.head.value != null, mustBeSpecified(TheId)))

    override def stateConditions: List[(Boolean, String)] = List()
  }

  def apply(form: Form, controller: EditorController): NewPathwayConditionOkListener =
    NewPathwayConditionOkListener(form, controller)
}

object NewPathwayPrerequisiteNextFormOkListener {

  //match requires stable identifiers
  val WisdomString: String = Wisdom.toString
  val CharismaString: String = Charisma.toString
  val StrengthString: String = Strength.toString
  val DexterityString: String = Dexterity.toString
  val IntelligenceString: String = Intelligence.toString
  val ConstitutionString: String = Constitution.toString

  private val DefaultMinimumValue: Int = 10

  case class NewPathwayPrerequisiteNextFormOkListener(override val form: Form,
                                                      override val controller: EditorController,
                                                      originNodeId: Int)
    extends OkFormButtonListener(form, controller) {

    private def showStatValueForm(): Unit = {
      val statValueForm: Form = FormBuilder()
        .addComboField(
          "Choose the Stat",
          List(
            CharismaString,
            ConstitutionString,
            DexterityString,
            IntelligenceString,
            StrengthString,
            WisdomString
          )
        )
        .addSpinnerNumberField("Minimum value required", DefaultMinimumValue)
        .get(controller)
      statValueForm.setOkButtonListener(
        NewStatPrerequisiteOkListener(statValueForm, controller, originNodeId, form.elements.head.value.toInt)
      )
      statValueForm.render()
    }

    private def showKeyItemForm(): Unit = {

    }

    override def performAction(): Unit = form.elements(1).value match {
      case StatValueOption => showStatValueForm()
      case KeyItemOption => showKeyItemForm()
    }

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List()
  }

  def apply(form: Form, controller: EditorController, originNodeId: Int): NewPathwayPrerequisiteNextFormOkListener =
    NewPathwayPrerequisiteNextFormOkListener(form, controller, originNodeId)
}

