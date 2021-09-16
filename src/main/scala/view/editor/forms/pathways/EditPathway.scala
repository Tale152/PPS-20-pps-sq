package view.editor.forms.pathways

import controller.editor.EditorController
import view.editor.okButtonListener.pathways.EditPathwayOkListener
import view.form.{Form, FormBuilder}

object EditPathway {

  val OriginNodeIdIndex: Int = 0

  def showEditPathwayForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "Which story node the pathway starts from?",
        editorController.getNodesIds(n => n.pathways.nonEmpty).map(id => id.toString)
      )
      .get(editorController)
    form.setOkButtonListener(EditPathwayOkListener(form, editorController))
    form.render()
  }

}
