package view.editor.forms.pathways

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.forms.EditorForm
import view.editor.okButtonListener.pathways.DeletePathwayOkListener
import view.form.{Form, FormBuilder}

object DeletePathway {
  val OriginNodeIdIndex: Int = 0
}

/** @inheritdoc */
case class DeletePathway() extends EditorForm {

  override def show(editorController: EditorController): Unit = {
    val targetOriginNodes = editorController.nodesControls.getNodesIds(n =>
      editorController.pathwaysControls.containsDeletablePathways(n)
    )
    if(targetOriginNodes.nonEmpty){
      val form: Form = FormBuilder()
        .addComboField("Which story node the pathway starts from?", targetOriginNodes.map(id => id.toString))
        .get(editorController)
      form.setOkButtonListener(DeletePathwayOkListener(form, editorController))
      form.render()
    } else {
      EditorView.showForbiddenActionDialog("There are no deletable pathways")
    }
  }

}
