package view.editor.forms.pathways

import controller.editor.EditorController
import view.editor.okButtonListener.pathways.NewPathwayOkListener
import view.form.{Form, FormBuilder}

object NewPathway {

  val OriginNodeIdIndex: Integer = 0

  def showNewPathwayForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "Which story node is the origin node?",
        editorController.getNodesIds(_ => true).map(id => id.toString)
      )
      .get(editorController)
    form.setOkButtonListener(NewPathwayOkListener(form, editorController))
    form.render()
  }
}