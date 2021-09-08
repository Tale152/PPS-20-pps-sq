package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.Form
import view.editor.FormConditionValues.ConditionDescriptions._
import view.editor.FormConditionValues.Conditions.NonEmptyString

case class NewStoryNodeOkListener(override val form: Form, override val editorController: EditorController)
  extends OkFormButtonListener(form, editorController) {

  override def performAction(): Unit = {
    editorController.addNewStoryNode(
      form.elements.head.value.toInt,
      form.elements(1).value,
      form.elements(2).value
    )
    editorController.execute()
  }

  /**
   * Specify the conditions and describe them.
   * If ALL are satisfied (&&) call [[OkFormButtonListener#performAction()]].
   *
   * @return a List containing a condition and it's textual description.
   */
  override def conditions: List[(Boolean, String)] = {
    List(
      (NonEmptyString(form.elements.head.value), InvalidIDMessage),
      (NonEmptyString(form.elements(1).value), InvalidDescriptionMessage),
      (NonEmptyString(form.elements(2).value), InvalidNarrativeMessage),
      (
        editorController.storyNodeExists(form.elements.head.value.toInt),
        StoryNodeDoesNotExists
      )
    )
  }
}
