package view.editor.forms.pathways

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.okButtonListener.pathways
import view.form.{Form, FormBuilder}

object DeletePathway {

  val OriginNodeIdIndex: Int = 0

  def showDeletePathwayForm(editorController: EditorController): Unit = {
    val targetOriginNodes = editorController.getNodesIds(n => editorController.containsDeletablePathways(n))
    if(targetOriginNodes.nonEmpty){
      val form: Form = FormBuilder()
        .addComboField("Which story node the pathway starts from?", targetOriginNodes.map(id => id.toString))
        //.addIntegerField("Which story node the pathway ends to? (id)")
        .get(editorController)
      form.setOkButtonListener(pathways.DeletePathwayOkListener(form, editorController))
      form.render()
    } else {
      EditorView.showForbiddenActionDialog("There are no deletable pathways")
    }

  }

}
