package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.Form
import view.editor.FormConditionValues.InputPredicates.NonEmptyString

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
  override def inputConditions: List[(Boolean, String)] = {
    List(
      (NonEmptyString(form.elements.head.value), ""),
      (NonEmptyString(form.elements(1).value), ""),
      (NonEmptyString(form.elements(2).value), ""),
      (
        editorController.storyNodeExists(form.elements.head.value.toInt),
        ""
      )
    )
  }

  /**
   * Specify the conditions to check in the state of the model.
   * If ALL are satisfied (&&) call [[OkFormButtonListener#performAction()]].
   *
   * @return a List containing conditions that are state based with textual descriptions.
   */
  override def stateConditions: List[(Boolean, String)] = List()
}
