package view.editor.okButtonListener.events

import controller.editor.EditorController
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.TheEvent
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.form.{Form, FormBuilder, OkFormButtonListener}

object DeleteEventOkListener {

  case class SelectStoryNodeOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    private def createComboList(id: Int): List[String] =
      controller.getStoryNode(id).get.events.zipWithIndex.map(x => "[" + (x._2 + 1) + "] " + x._1.description)

    private def showDeleteEventForm(id: Int): Unit = {
      val form: Form = FormBuilder()
        .addComboField("Which event you want to delete?", createComboList(id))
        .get(controller)
      form.setOkButtonListener(DeleteEventOkListener(form, controller, id))
      form.render()
    }

    override def performAction(): Unit = showDeleteEventForm(form.elements.head.value.toInt)

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List()
  }

  case class DeleteEventOkListener(override val form: Form, override val controller: EditorController, id: Int)
    extends EditorOkFormButtonListener(form, controller) {

    override def editorControllerAction(): Unit = {
      val selectedString: String = form.elements.head.value
      val selectedIndex: Int = selectedString.substring(0, selectedString.indexOf("]")).replace("[", "").toInt - 1
      controller.deleteEventFromNode(id, controller.getStoryNode(id).get.events(selectedIndex))
    }

    override def inputConditions: List[(Boolean, String)] =
      List((NonEmptyString(form.elements.head.value), mustBeSpecified(TheEvent)))

    override def stateConditions: List[(Boolean, String)] = List()
  }
}
