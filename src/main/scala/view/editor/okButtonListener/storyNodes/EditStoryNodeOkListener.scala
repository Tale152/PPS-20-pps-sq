package view.editor.okButtonListener.storyNodes

import controller.editor.EditorController
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.TheDescription
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.forms.storyNodes.EditStoryNode.NodeToEditIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.editor.okButtonListener.storyNodes.EditStoryNodeOkListener.StoryNodeNarrativeIndex
import view.form.{Form, FormBuilder, OkFormButtonListener}

object EditStoryNodeOkListener {

  val StoryNodeNarrativeIndex: Int = 0

  private case class EditStoryNodeOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    private def showEditStoryNodeFormFields(id: Int, oldNarrative: String): Unit = {
      val form: Form = FormBuilder()
        .addTextAreaField("What narrative should the story node show?", oldNarrative)
        .get(controller)
      form.setOkButtonListener(EditStoryNodeNextFormOkListener(form, controller, id))
      form.render()
    }

    override def performAction(): Unit =
      showEditStoryNodeFormFields(
        form.elements(NodeToEditIdIndex).value.toInt,
        controller.getStoryNode(form.elements(NodeToEditIdIndex).value.toInt).get.narrative
      )

    override def inputConditions: List[(Boolean, String)] = List() //route node always exists

    override def stateConditions: List[(Boolean, String)] = List()
  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener =
    EditStoryNodeOkListener(form, controller)

}

private case class EditStoryNodeNextFormOkListener(override val form: Form,
                                                   override val controller: EditorController,
                                                   id: Int)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit =
    controller.editExistingStoryNode(id, form.elements(StoryNodeNarrativeIndex).value)

  override def inputConditions: List[(Boolean, String)] =
    List((NonEmptyString(form.elements(StoryNodeNarrativeIndex).value), mustBeSpecified(TheDescription)))

  override def stateConditions: List[(Boolean, String)] = List()

}
