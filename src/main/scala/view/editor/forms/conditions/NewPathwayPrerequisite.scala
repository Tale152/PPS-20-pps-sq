package view.editor.forms.conditions

import controller.editor.EditorController
import view.editor.okButtonListener.conditions.NewPathwayPrerequsiteOkListener
import view.form.{Form, FormBuilder}

object NewPathwayPrerequisite {

  def showNewPathwayPrerequisiteForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "Select the origin node of the pathway (id)",
        editorController.getNodesIds(n => n.pathways.count(p => p.prerequisite.isEmpty) >= 2).map(n => n.toString)
      )
      .get(editorController)
    form.setOkButtonListener(NewPathwayPrerequsiteOkListener(form, editorController))
    form.render()
  }
}
