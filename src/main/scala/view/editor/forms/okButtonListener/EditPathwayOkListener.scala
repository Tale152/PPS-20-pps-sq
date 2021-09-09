package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.FormConditionValues.ConditionDescriptions.Subjects._
import view.editor.FormConditionValues.ConditionDescriptions.{isNotValid, mustBeSpecified}
import view.editor.{Form, FormBuilder}
import view.editor.FormConditionValues.InputPredicates.NonEmptyString

object EditPathwayOkListener {
  case class SelectPathwayOkListener(override val form: Form, override val editorController: EditorController)
    extends OkFormButtonListener(form, editorController) {

    private def showEditPathwayFormFields(startNodeId: Int, endNodeId: Int, oldDescription: String): Unit = {
      val form: Form = FormBuilder()
        .addTextAreaField("What description should the pathway show?", oldDescription)
        .get(editorController)
      form.setOkButtonListener(EditPathwayOkListener(form, editorController,startNodeId, endNodeId))
      form.render()
    }

    override def performAction(): Unit = showEditPathwayFormFields(
      form.elements.head.value.toInt,
      form.elements(1).value.toInt,
      editorController.getPathway(form.elements.head.value.toInt, form.elements(1).value.toInt).description
    )

    override def inputConditions: List[(Boolean, String)] =
      List(
        (NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)),
        (NonEmptyString(form.elements(1).value), mustBeSpecified(TheEndingId)),
      )

    override def stateConditions: List[(Boolean, String)] =
      List(
        (editorController.pathwayExists(form.elements.head.value.toInt, form.elements(1).value.toInt),
          isNotValid(ThePathway))
      )
  }

  case class EditPathwayOkListener(override val form: Form,
                                   override val editorController: EditorController,
                                   startNodeId: Int,
                                   endNodeId: Int)
    extends EditorOkFormButtonListener(form, editorController) {

    override def editorControllerAction(): Unit =
      editorController.editExistingPathway(startNodeId, endNodeId, form.elements.head.value)

    override def inputConditions: List[(Boolean, String)] =
      List((NonEmptyString(form.elements.head.value), mustBeSpecified(TheDescription)))

    override def stateConditions: List[(Boolean, String)] = List()

  }
}

