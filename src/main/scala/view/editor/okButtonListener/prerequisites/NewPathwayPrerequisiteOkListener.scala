package view.editor.okButtonListener.prerequisites

import controller.editor.EditorController
import view.editor.okButtonListener.prerequisites.NewPathwayPrerequisiteOkListener._
import view.editor.util.IndexedComboListUtil.createIndexedOption
import view.editor.util.StatsNameStringUtil.StatStrings
import view.form.{Form, FormBuilder, OkFormButtonListener}

object NewPathwayPrerequisiteOkListener {

  val DestinationNodeIdIndex: Int = 0
  val PrerequisiteOptionIndex: Int = 1

  val StatValueOption: String = "Stat value"
  val KeyItemOption: String = "Key item"

  private case class NewPathwayConditionOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    override def performAction(): Unit = {
      val nextForm: Form = FormBuilder()
        .addComboField(
          "Select the destination node of the pathway",
          controller.nodesControls.getStoryNode(form.elements.head.value.toInt).get
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

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List()
  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener =
    NewPathwayConditionOkListener(form, controller)
}

object NewPathwayPrerequisiteNextFormOkListener {

  private val DefaultMinimumValue: Int = 10

  val StatValueFormStatIndex: Int = 0
  val StatValueFormValueIndex: Int = 1

  val KeyItemFormItemIndex: Int = 0

  case class NewPathwayPrerequisiteNextFormOkListener(override val form: Form,
                                                      override val controller: EditorController,
                                                      originNodeId: Int)
    extends OkFormButtonListener(form, controller) {

    private def showStatValueForm(): Unit = {
      val statValueForm: Form = FormBuilder()
        .addComboField("Choose the Stat", StatStrings)
        .addSpinnerNumberField("Minimum value required", DefaultMinimumValue)
        .get(controller)
      statValueForm.setOkButtonListener(
        NewStatPrerequisiteOkListener(statValueForm, controller, originNodeId, form.elements.head.value.toInt)
      )
      statValueForm.render()
    }

    private def showKeyItemForm(): Unit = {
      val statValueForm: Form = FormBuilder()
        .addComboField(
          "Choose the required Key Item",
          controller.nodesControls.getAllKeyItemsBeforeNode(controller.nodesControls.getStoryNode(originNodeId).get)
            .zipWithIndex.map(x => createIndexedOption(x._2, x._1.name))
        )
        .get(controller)
      statValueForm.setOkButtonListener(
        NewKeyItemPrerequisiteOkListener(
          statValueForm,
          controller,
          originNodeId,
          form.elements(DestinationNodeIdIndex).value.toInt
        )
      )
      statValueForm.render()
    }

    override def performAction(): Unit = form.elements(PrerequisiteOptionIndex).value match {
      case StatValueOption => showStatValueForm()
      case KeyItemOption => showKeyItemForm()
    }

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List(
      (form.elements(PrerequisiteOptionIndex).value != KeyItemOption ||
        controller.nodesControls
          .getAllKeyItemsBeforeNode(controller.nodesControls.getStoryNode(originNodeId).get).nonEmpty,
        "There are no key items in node " + originNodeId + " or before")
    )
  }

  def apply(form: Form, controller: EditorController, originNodeId: Int): NewPathwayPrerequisiteNextFormOkListener =
    NewPathwayPrerequisiteNextFormOkListener(form, controller, originNodeId)
}

