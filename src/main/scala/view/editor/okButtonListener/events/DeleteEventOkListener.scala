package view.editor.okButtonListener.events

import controller.editor.EditorController
import view.editor.forms.events.DeleteEvent.StoryNodeIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListenerUnconditional
import view.editor.okButtonListener.events.DeleteEventOkListener.EventComboIndex
import view.editor.util.IndexedComboListUtil.{createIndexedOption, extractIndexFromOption}
import view.form.{Form, FormBuilder, OkFormButtonListener, OkFormButtonListenerUnconditional}

object DeleteEventOkListener {

  val EventComboIndex: Int = 0

  private case class DeleteEventOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListenerUnconditional(form, controller) {

    private def createComboList(id: Int): List[String] =
      controller.nodesControls.storyNode(id).get
        .events.zipWithIndex.map(x => createIndexedOption(x._2, x._1.description))

    private def showDeleteEventForm(id: Int): Unit = {
      val nextForm: Form = FormBuilder()
        .addComboField("Which event you want to delete?", createComboList(id))
        .get(controller)
      nextForm.setOkButtonListener(DeleteEventNextFormOkListener(nextForm, controller, id))
      nextForm.render()
    }

    override def performAction(): Unit = showDeleteEventForm(form.elements(StoryNodeIdIndex).value.toInt)

  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener =
    DeleteEventOkListener(form, controller)

}

private case class DeleteEventNextFormOkListener(override val form: Form,
                                                 override val controller: EditorController,
                                                 nodeId: Int)
  extends EditorOkFormButtonListenerUnconditional(form, controller) {

  override def editorControllerAction(): Unit = {
    val selectedIndex: Int = extractIndexFromOption(form.elements(EventComboIndex).value)
    controller.nodesControls.deleteEventFromNode(
      nodeId,
      controller.nodesControls.storyNode(nodeId).get.events(selectedIndex)
    )
  }

}
