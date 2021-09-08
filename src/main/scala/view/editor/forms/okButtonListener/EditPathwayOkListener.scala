package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.{Form, FormBuilder}
import view.editor.FormConditionValues.ConditionDescriptions._
import view.editor.FormConditionValues.Conditions.NonEmptyString

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

    override def conditions: List[(Boolean, String)] = List(
      (NonEmptyString(form.elements.head.value), InvalidStartingIDMessage),
      (NonEmptyString(form.elements(1).value), InvalidEndingIDMessage),
      (editorController.pathwayExists(form.elements.head.value.toInt, form.elements(1).value.toInt),
        "The chosen Pathway is not valid.")
    )
  }

  case class EditPathwayOkListener(override val form: Form,
                                   override val editorController: EditorController,
                                   startNodeId: Int,
                                   endNodeId: Int)
    extends OkFormButtonListener(form, editorController) {

    override def performAction(): Unit =
      editorController.editExistingPathway(startNodeId, endNodeId, form.elements.head.value)

    override def conditions: List[(Boolean, String)] =
      List((NonEmptyString(form.elements.head.value), InvalidDescriptionMessage))
  }
}

