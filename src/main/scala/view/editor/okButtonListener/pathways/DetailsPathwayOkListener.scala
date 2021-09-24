package view.editor.okButtonListener.pathways

import controller.editor.EditorController
import view.editor.PathwayDetailsView
import view.editor.forms.pathways.DetailsPathway.OriginNodeIdIndex
import view.editor.okButtonListener.pathways.DetailsPathwayOkListener.DestinationNodeIdIndex
import view.form.{Form, FormBuilder, OkFormButtonListener, OkFormButtonListenerUnconditional}

object DetailsPathwayOkListener {

  val DestinationNodeIdIndex: Int = 0

  private case class DetailsPathwayOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListenerUnconditional(form, controller) {

    override def performAction(): Unit = {
      val nextForm: Form = FormBuilder()
        .addComboField(
          "Which story node the pathway ends to?",
          controller.nodesControls.getStoryNode(form.elements(OriginNodeIdIndex).value.toInt).get
            .mutablePathways.toList.map(p => p.destinationNode.id.toString))
        .get(controller)
      nextForm.setOkButtonListener(
        DetailsPathwayNextFormOkListener(nextForm, controller, form.elements(OriginNodeIdIndex).value.toInt)
      )
      nextForm.render()
    }

  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener = DetailsPathwayOkListener(form, controller)

}

private case class DetailsPathwayNextFormOkListener(override val form: Form,
                                                 override val controller: EditorController,
                                                 originNodeId: Int)
  extends OkFormButtonListenerUnconditional(form, controller) {

  override def performAction(): Unit = PathwayDetailsView(
    controller.nodesControls.getStoryNode(originNodeId).get,
    controller.pathwaysControls.getPathway(originNodeId, form.elements(DestinationNodeIdIndex).value.toInt).get,
    controller
  ).render()

}
