package view.editor.okButtonListener.storyNodes

import controller.editor.EditorController
import view.editor.StoryNodeDetailsView
import view.editor.forms.storyNodes.DetailsStoryNode.StoryNodeIdIndex
import view.form.{Form, OkFormButtonListenerUnconditional}

case class DetailsStoryNodeOkListener(override val form: Form, override val controller: EditorController)
  extends OkFormButtonListenerUnconditional(form, controller) {

  override def performAction(): Unit =
    StoryNodeDetailsView(
      controller.nodesControls.getStoryNode(form.elements(StoryNodeIdIndex).value.toInt).get,
      controller
    ).render()
}
