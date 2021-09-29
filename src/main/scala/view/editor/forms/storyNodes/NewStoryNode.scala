package view.editor.forms.storyNodes

import controller.editor.EditorController
import view.editor.forms.EditorForm
import view.editor.okButtonListener.storyNodes.NewStoryNodeOkListener
import view.form.{Form, FormBuilder}

object NewStoryNode {
  val StartingNodeIndex: Int = 0
  val NewPathwayDescriptionIndex: Int = 1
  val NewNodeNarrativeIndex: Int = 2
}

/** @inheritdoc */
case class NewStoryNode() extends EditorForm {

  override def show(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "Which story node is the starting node?",
        editorController.nodesControls.nodesIds(_ => true).map(id => id.toString)
      )
      .addTextField("What description should the pathway to the new story node show?")
      .addTextAreaField("What narrative should the new story node show?")
      .get(editorController)
    form.setOkButtonListener(NewStoryNodeOkListener(form, editorController))
    form.render()
  }

}
