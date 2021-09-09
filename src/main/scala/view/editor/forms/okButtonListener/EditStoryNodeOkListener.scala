package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.FormConditionValues.ConditionDescriptions.Subjects.{TheDescription, TheId, TheStoryNode}
import view.editor.FormConditionValues.ConditionDescriptions.{doesNotExists, mustBeSpecified}
import view.editor.FormConditionValues.InputPredicates.NonEmptyString
import view.editor.{Form, FormBuilder}

object EditStoryNodeOkListener {

  case class SelectStoryNodeOkListener(override val form: Form, override val editorController: EditorController)
    extends OkFormButtonListener(form, editorController) {

    private def showEditStoryNodeFormFields(id: Int, oldNarrative: String): Unit = {
      val form: Form = FormBuilder()
        .addTextAreaField("What narrative should the story node show?", oldNarrative)
        .get(editorController)
      form.setOkButtonListener(EditStoryNodeOkListener(form, editorController, id))
      form.render()
    }

    override def performAction(): Unit =
      showEditStoryNodeFormFields(
        form.elements.head.value.toInt,
        editorController.getStoryNode(form.elements.head.value.toInt).narrative
      )

    override def inputConditions: List[(Boolean, String)] =
      List(
        (NonEmptyString(form.elements.head.value), mustBeSpecified(TheId)),
      )

    override def stateConditions: List[(Boolean, String)] =
      List(
        (editorController.storyNodeExists(form.elements.head.value.toInt), doesNotExists(TheStoryNode))
      )
  }

  case class EditStoryNodeOkListener(override val form: Form, override val editorController: EditorController, id: Int)
    extends EditorOkFormButtonListener(form, editorController) {

    override def editorControllerAction(): Unit =
      editorController.editExistingStoryNode(id, form.elements.head.value)

    override def inputConditions: List[(Boolean, String)] =
      List(
        (NonEmptyString(form.elements.head.value), mustBeSpecified(TheDescription)),
      )

    override def stateConditions: List[(Boolean, String)] = List()

  }

}
