package view.editor.okButtonListener.conditions

import controller.editor.EditorController
import view.editor.forms.conditions.DeletePathwayPrerequisite.OriginStoryNodeIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.editor.okButtonListener.conditions.DeletePathwayPrerequisiteOkListener.DestinationStoryNodeIdIndex
import view.form.{Form, FormBuilder, OkFormButtonListener}

object DeletePathwayPrerequisiteOkListener {

  val DestinationStoryNodeIdIndex: Int = 0

  private case class DeletePathwayPrerequisiteOkListener(override val form: Form,
                                                 override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    override def performAction(): Unit = {
      val nextForm: Form = FormBuilder()
        .addComboField(
          "Select the destination node of the pathway (id)",
          controller.getStoryNode(form.elements.head.value.toInt).get
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

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List()
  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener =
    DeletePathwayPrerequisiteOkListener(form, controller)
}

private case class DeletePathwayPrerequisiteNextFormOkListener(override val form: Form,
                                                               override val controller: EditorController,
                                                               originNodeId: Int)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit =
    controller.deletePrerequisiteFromPathway(originNodeId, form.elements(DestinationStoryNodeIdIndex).value.toInt)

  override def inputConditions: List[(Boolean, String)] = List()

  override def stateConditions: List[(Boolean, String)] = List()
}
