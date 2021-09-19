package view.editor.forms.pathways

import controller.editor.EditorController
import view.editor.okButtonListener.pathways.DetailsPathwayOkListener
import view.form.{Form, FormBuilder}

object DetailsPathway {

  val OriginNodeIdIndex: Int = 0

  def showDetailsPathwayForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "Which is the starting node?",
        editorController.nodesControls.getNodesIds(n => n.pathways.nonEmpty).map(id => id.toString)
      )
      .get(editorController)
    form.setOkButtonListener(DetailsPathwayOkListener(form, editorController))
    form.render()
  }

}
