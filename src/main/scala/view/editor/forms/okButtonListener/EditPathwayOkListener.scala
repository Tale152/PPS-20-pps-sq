package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects._
import view.editor.forms.EditorConditionValues.ConditionDescriptions.{isNotValid, mustBeSpecified}
import view.form.{Form, FormBuilder, OkFormButtonListener}
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString

object EditPathwayOkListener {
  case class SelectPathwayOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    private def showEditPathwayFormFields(startNodeId: Int, endNodeId: Int, oldDescription: String): Unit = {
      val form: Form = FormBuilder()
        .addTextAreaField("What description should the pathway show?", oldDescription)
        .get(controller)
      form.setOkButtonListener(EditPathwayOkListener(form, controller, startNodeId, endNodeId))
      form.render()
    }

    override def performAction(): Unit = showEditPathwayFormFields(
      form.elements.head.value.toInt,
      form.elements(1).value.toInt,
      controller.getPathway(form.elements.head.value.toInt, form.elements(1).value.toInt).get.description
    )

    override def inputConditions: List[(Boolean, String)] =
      List(
        (NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)),
        (NonEmptyString(form.elements(1).value), mustBeSpecified(TheEndingId)),
      )

    override def stateConditions: List[(Boolean, String)] =
      List(
        (controller.getPathway(form.elements.head.value.toInt, form.elements(1).value.toInt).isDefined,
          isNotValid(ThePathway))
      )
  }

  case class EditPathwayOkListener(override val form: Form,
                                   override val controller: EditorController,
                                   startNodeId: Int,
                                   endNodeId: Int)
    extends EditorOkFormButtonListener(form, controller) {

    override def editorControllerAction(): Unit =
      controller.editExistingPathway(startNodeId, endNodeId, form.elements.head.value)

    override def inputConditions: List[(Boolean, String)] =
      List((NonEmptyString(form.elements.head.value), mustBeSpecified(TheDescription)))

    override def stateConditions: List[(Boolean, String)] = List()

  }
}

