package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.form.{Form, OkFormButtonListener}

/**
 * OkButtonLister used to re-render the editor view at the end of the [[controller.editor.EditorController]] command.
 *
 * @param form             the Form where the elements are.
 * @param editorController the Editor Controller.
 */
abstract class EditorOkFormButtonListener(override val form: Form, override val editorController: EditorController)
  extends OkFormButtonListener(form, editorController) {

  override def performAction(): Unit = {
    editorControllerAction()
    editorController.execute()
  }

  /**
   * Execute an operation before rendering the [[view.editor.EditorView]].
   */
  def editorControllerAction(): Unit

}
