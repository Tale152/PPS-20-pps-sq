package view.editor.forms

import controller.editor.EditorController

/**
 * Class used to show a form in the editor.
 */
abstract class EditorForm {

  /**
   * Shows the form in the GUI.
   * @param editorController controller used to manipulate the story
   */
  def show(editorController: EditorController): Unit

}
