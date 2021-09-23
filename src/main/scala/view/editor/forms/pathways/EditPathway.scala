package view.editor.forms.pathways

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.forms.EditorForm
import view.editor.okButtonListener.pathways.EditPathwayOkListener
import view.form.{Form, FormBuilder}

object EditPathway {
  val OriginNodeIdIndex: Int = 0
}

/** @inheritdoc */
case class EditPathway() extends EditorForm {

  override def show(editorController: EditorController): Unit = {
    if(editorController.nodesControls.getStoryNode(0).get.pathways.nonEmpty){
      val form: Form = FormBuilder()
        .addComboField(
          "Which story node the pathway starts from?",
          editorController.nodesControls.getNodesIds(n => n.pathways.nonEmpty).map(id => id.toString)
        )
        .get(editorController)
      form.setOkButtonListener(EditPathwayOkListener(form, editorController))
      form.render()
    } else {
      EditorView.showForbiddenActionDialog("There are no existing pathways")
    }
  }

}
