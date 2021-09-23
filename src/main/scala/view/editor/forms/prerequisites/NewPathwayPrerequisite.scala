package view.editor.forms.prerequisites

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.forms.EditorForm
import view.editor.okButtonListener.prerequisites.NewPathwayPrerequisiteOkListener
import view.form.{Form, FormBuilder}

object NewPathwayPrerequisite {
  val OriginNodeIdIndex: Int = 0
}

/** @inheritdoc */
case class NewPathwayPrerequisite() extends EditorForm {

  override def show(editorController: EditorController): Unit = {
    if(editorController.nodesControls.getStoryNode(0).get.pathways.isEmpty){
      EditorView.showForbiddenActionDialog("There are no pathways")
    } else {
      val targetNodes = editorController
        .nodesControls
        .getNodesIds(n => n.pathways.count(p => p.prerequisite.isEmpty) >= 2)
        .map(n => n.toString)
      if(targetNodes.nonEmpty){
        val form: Form = FormBuilder()
          .addComboField("Select the origin node of the pathway", targetNodes)
          .get(editorController)
        form.setOkButtonListener(NewPathwayPrerequisiteOkListener(form, editorController))
        form.render()
      } else {
        EditorView.showForbiddenActionDialog("There are no valid pathways to put a prerequisite on")
      }
    }
  }

}
