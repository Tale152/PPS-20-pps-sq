package view.editor.forms.events

import controller.editor.EditorController
import view.editor.okButtonListener.events.NewEventOkListener
import view.form.{Form, FormBuilder}

object NewEvent {

  val StatModifierOption: String = "Attach stat modifier"
  val ItemOption: String = "Find item"

  val NodeIdIndex: Int = 0
  val EventGenreIndex: Int = 1

  def showNewEventForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "In which node you want to want to insert an event?",
        editorController.nodesControls.getNodesIds(_ => true).map(n => n.toString)
      )
      .addComboField("Which genre of event you want to create?", List(StatModifierOption, ItemOption))
      .get(editorController)
    form.setOkButtonListener(NewEventOkListener(form, editorController))
    form.render()
  }
}
