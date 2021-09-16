package view.editor.forms.conditions

import controller.editor.EditorController
import view.editor.okButtonListener.conditions.DeletePathwayPrerequisiteOkListener
import view.form.{Form, FormBuilder}

object DeletePathwayPrerequisite {

  def showDeletePathwayPrerequisiteForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "Select the origin node of the pathway (id)",
        editorController.getNodesIds(n => n.pathways.exists(p => p.prerequisite.nonEmpty)).map(n => n.toString)
      )
      .get(editorController)
    form.setOkButtonListener(DeletePathwayPrerequisiteOkListener(form, editorController))
    form.render()
  }
}
