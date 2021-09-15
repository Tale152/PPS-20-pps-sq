package view.editor.forms.okButtonListener.storyNodes

import controller.editor.EditorController
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects.{TheDescription, TheId, TheStoryNode}
import view.editor.forms.EditorConditionValues.ConditionDescriptions.{doesNotExists, mustBeSpecified}
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.forms.okButtonListener.EditorOkFormButtonListener
import view.form.{Form, FormBuilder, OkFormButtonListener}

object EditStoryNodeOkListener {

  case class SelectStoryNodeOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    private def showEditStoryNodeFormFields(id: Int, oldNarrative: String): Unit = {
      val form: Form = FormBuilder()
        .addTextAreaField("What narrative should the story node show?", oldNarrative)
        .get(controller)
      form.setOkButtonListener(EditStoryNodeOkListener(form, controller, id))
      form.render()
    }

    override def performAction(): Unit =
      showEditStoryNodeFormFields(
        form.elements.head.value.toInt,
        controller.getStoryNode(form.elements.head.value.toInt).get.narrative
      )

    override def inputConditions: List[(Boolean, String)] =
      List(
        (NonEmptyString(form.elements.head.value), mustBeSpecified(TheId)),
      )

    override def stateConditions: List[(Boolean, String)] =
      List(
        (controller.getStoryNode(form.elements.head.value.toInt).isDefined, doesNotExists(TheStoryNode))
      )
  }

  case class EditStoryNodeOkListener(override val form: Form, override val controller: EditorController, id: Int)
    extends EditorOkFormButtonListener(form, controller) {

    override def editorControllerAction(): Unit =
      controller.editExistingStoryNode(id, form.elements.head.value)

    override def inputConditions: List[(Boolean, String)] =
      List(
        (NonEmptyString(form.elements.head.value), mustBeSpecified(TheDescription)),
      )

    override def stateConditions: List[(Boolean, String)] = List()

  }

}
