package view.editor.forms.storyNodes

import controller.editor.EditorController
import view.editor.forms.EditorForm
import view.editor.okButtonListener.storyNodes.DetailsStoryNodeOkListener
import view.form.{Form, FormBuilder}

object DetailsStoryNode {
  val StoryNodeIdIndex: Int = 0
}

/** @inheritdoc */
case class DetailsStoryNode() extends EditorForm {

  override def show(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "Which story node would you like to examine?",
        editorController.nodesControls.nodesIds(_ => true).map(id => id.toString)
      )
      .get(editorController)
    form.setOkButtonListener(DetailsStoryNodeOkListener(form, editorController))
    form.render()
  }

}
