package view.editor.forms.events

import controller.editor.EditorController
import view.editor.okButtonListener.events.DeleteEventOkListener
import view.form.{Form, FormBuilder}

object DeleteEvent {

  val StoryNodeId: Int = 0

  def showDeleteEventForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "Which story node contains the event? (id)",
        editorController.getNodesIds(n => n.events.nonEmpty).map(id => id.toString)
      )
      .get(editorController)
    form.setOkButtonListener(DeleteEventOkListener(form, editorController))
    form.render()
  }
}
