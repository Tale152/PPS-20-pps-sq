package view.editor.forms.events

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.forms.EditorForm
import view.editor.okButtonListener.events.DeleteEventOkListener
import view.form.{Form, FormBuilder}

object DeleteEvent {
  val StoryNodeIdIndex: Int = 0
}

/** @inheritdoc */
case class DeleteEvent() extends EditorForm {

  override def show(editorController: EditorController): Unit = {
    val targetNodes = editorController.nodesControls.nodesIds(n => n.events.nonEmpty).map(id => id.toString)
    if(targetNodes.nonEmpty){
      val form: Form = FormBuilder()
        .addComboField("Which story node contains the event?", targetNodes)
        .get(editorController)
      form.setOkButtonListener(DeleteEventOkListener(form, editorController))
      form.render()
    } else {
      EditorView.showForbiddenActionDialog("There are no existing events")
    }
  }

}
