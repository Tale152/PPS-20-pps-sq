package view.editor.okButtonListener.pathways

import controller.editor.EditorController
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects._
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.forms.pathways.EditPathway.OriginNodeIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.editor.okButtonListener.pathways.EditPathwayOkListener.DestinationNodeIdIndex
import view.form.{Form, FormBuilder, OkFormButtonListener}

object EditPathwayOkListener {

  val DestinationNodeIdIndex: Int = 0

  private case class EditPathwayOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    override def performAction(): Unit = {
      val newForm: Form = FormBuilder()
        .addComboField(
          "Which story node the pathway ends to?",
          controller.getStoryNode(form.elements(OriginNodeIdIndex).value.toInt).get
            .mutablePathways.toList.map(p => p.destinationNode.id.toString))
        .get(controller)
      newForm.setOkButtonListener(
        EditPathwayNextFormOkListener(newForm, controller, form.elements(OriginNodeIdIndex).value.toInt)
      )
      newForm.render()
    }

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List()
  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener = EditPathwayOkListener(form, controller)

}

private case class EditPathwayNextFormOkListener(override val form: Form,
                                                 override val controller: EditorController,
                                                 originNodeId: Int)
  extends OkFormButtonListener(form, controller) {

  override def performAction(): Unit = {
    val lastForm: Form = FormBuilder()
      .addTextAreaField(
        "Pathway description",
        controller.getPathway(originNodeId, form.elements(DestinationNodeIdIndex).value.toInt).get.description
      ).get(controller)
    lastForm.setOkButtonListener(
      EditPathwayLastFormOkListener(
        lastForm,
        controller,
        originNodeId,
        form.elements(DestinationNodeIdIndex).value.toInt
      )
    )
    lastForm.render()
  }

  override def inputConditions: List[(Boolean, String)] = List()


  override def stateConditions: List[(Boolean, String)] = List()

}

private case class EditPathwayLastFormOkListener(override val form: Form,
                                                 override val controller: EditorController,
                                                 originNodeId: Int,
                                                 destinationNodeId: Int)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit =
    controller.editExistingPathway(originNodeId, destinationNodeId, form.elements.head.value)

  override def inputConditions: List[(Boolean, String)] =
    List((NonEmptyString(form.elements.head.value), mustBeSpecified(TheDescription)))

  override def stateConditions: List[(Boolean, String)] = List()
}
