package view.editor.forms.conditions

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.okButtonListener.conditions.DeletePathwayPrerequisiteOkListener
import view.form.{Form, FormBuilder}

object DeletePathwayPrerequisite {

  val OriginStoryNodeIdIndex: Int = 0

  def showDeletePathwayPrerequisiteForm(editorController: EditorController): Unit = {
    val targetNodes = editorController
      .getNodesIds(n => n.pathways.exists(p => p.prerequisite.nonEmpty))
      .map(n => n.toString)
    if(targetNodes.nonEmpty){
      val form: Form = FormBuilder()
        .addComboField("Select the origin node of the pathway (id)", targetNodes)
        .get(editorController)
      form.setOkButtonListener(DeletePathwayPrerequisiteOkListener(form, editorController))
      form.render()
    } else {
      EditorView.showForbiddenActionDialog("There are no pathways with prerequisites")
    }

  }
}
