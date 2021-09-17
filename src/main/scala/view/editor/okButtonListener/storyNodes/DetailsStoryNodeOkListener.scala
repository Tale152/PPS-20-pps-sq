package view.editor.okButtonListener.storyNodes

import controller.editor.EditorController
import view.editor.StoryNodeDetailsView
import view.editor.forms.storyNodes.DetailsStoryNode.StoryNodeIdIndex
import view.form.{Form, OkFormButtonListener}

case class DetailsStoryNodeOkListener(override val form: Form, override val controller: EditorController)
  extends OkFormButtonListener(form, controller) {

  override def performAction(): Unit =
    StoryNodeDetailsView(controller.getStoryNode(form.elements(StoryNodeIdIndex).value.toInt).get, controller).render()

  override def inputConditions: List[(Boolean, String)] = List()

  override def stateConditions: List[(Boolean, String)] = List()

}
