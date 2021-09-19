package view.editor.okButtonListener.pathways

import controller.editor.EditorController
import view.editor.forms.pathways.DeletePathway.OriginNodeIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.editor.okButtonListener.pathways.DeletePathwayOkListener.DestinationNodeIdIndex
import view.form.{Form, FormBuilder, OkFormButtonListener}

object DeletePathwayOkListener {

  val DestinationNodeIdIndex: Int = 0

  case class DeletePathwayOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    override def performAction(): Unit = {
      var originNodePathways = controller
        .nodesControls.getStoryNode(form.elements(OriginNodeIdIndex).value.toInt)
        .get.mutablePathways
      if(originNodePathways.exists(p => p.prerequisite.nonEmpty) &&
        originNodePathways.count(p => p.prerequisite.isEmpty) == 1){
          originNodePathways = originNodePathways.filter(p => p.prerequisite.isEmpty)
      }
      originNodePathways = originNodePathways.filter(p =>
        controller.pathwaysControls.getAllOriginNodes(p.destinationNode.id).size > 1
      )
      val newForm: Form = FormBuilder()
        .addComboField(
          "Which story node the pathway ends to?",
          originNodePathways.toList.map(p => p.destinationNode.id.toString))
        .get(controller)
      newForm.setOkButtonListener(
        DeletePathwayNextFormOkListener(newForm, controller, form.elements(OriginNodeIdIndex).value.toInt)
      )
      newForm.render()
    }

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List()

  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener = DeletePathwayOkListener(form, controller)

}

private case class DeletePathwayNextFormOkListener(override val form: Form,
                                                 override val controller: EditorController,
                                                 originNodeId: Int)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit =
    controller.pathwaysControls.deleteExistingPathway(originNodeId, form.elements(DestinationNodeIdIndex).value.toInt)

  override def inputConditions: List[(Boolean, String)] = List()

  override def stateConditions: List[(Boolean, String)] = List()
}
