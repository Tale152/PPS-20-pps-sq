package view.editor.okButtonListener.prerequisites

import controller.editor.EditorController
import view.editor.forms.prerequisites.DeletePathwayPrerequisite.OriginStoryNodeIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListenerUnconditional
import view.editor.okButtonListener.prerequisites.DeletePathwayPrerequisiteOkListener.DestinationStoryNodeIdIndex
import view.form.{Form, FormBuilder, OkFormButtonListener, OkFormButtonListenerUnconditional}

object DeletePathwayPrerequisiteOkListener {

  val DestinationStoryNodeIdIndex: Int = 0

  private case class DeletePathwayPrerequisiteOkListener(override val form: Form,
                                                 override val controller: EditorController)
    extends OkFormButtonListenerUnconditional(form, controller) {

    override def performAction(): Unit = {
      val nextForm: Form = FormBuilder()
        .addComboField(
          "Select the destination node of the pathway (id)",
          controller.nodesControls.storyNode(form.elements.head.value.toInt).get
            .mutablePathways.filter(p => p.prerequisite.nonEmpty)
            .map(p => p.destinationNode.id.toString).toSeq.toList
        )
        .get(controller)
      nextForm.setOkButtonListener(
        DeletePathwayPrerequisiteNextFormOkListener(
          nextForm, controller, form.elements(OriginStoryNodeIdIndex).value.toInt
        )
      )
      nextForm.render()
    }

  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener =
    DeletePathwayPrerequisiteOkListener(form, controller)
}

private case class DeletePathwayPrerequisiteNextFormOkListener(override val form: Form,
                                                               override val controller: EditorController,
                                                               originNodeId: Int)
  extends EditorOkFormButtonListenerUnconditional(form, controller) {

  override def editorControllerAction(): Unit = controller.pathwaysControls
      .deletePrerequisiteFromPathway(originNodeId, form.elements(DestinationStoryNodeIdIndex).value.toInt)
}
