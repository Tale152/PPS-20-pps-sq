package view.editor.forms.pathways

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.okButtonListener.pathways.NewPathwayOkListener
import view.form.{Form, FormBuilder}

object NewPathway {

  val OriginNodeIdIndex: Integer = 0

  def showNewPathwayForm(editorController: EditorController): Unit = {
    val targetNodes = editorController.pathwaysControls.getValidNodesForPathwayOrigin
    if(targetNodes.nonEmpty){
      val form: Form = FormBuilder()
        .addComboField(
          "Which story node is the origin node?",
          targetNodes.map(n => n.id.toString)
        )
        .get(editorController)
      form.setOkButtonListener(NewPathwayOkListener(form, editorController))
      form.render()
    } else {
      EditorView.showForbiddenActionDialog("There aren't new possible pathways")
    }
  }
}
