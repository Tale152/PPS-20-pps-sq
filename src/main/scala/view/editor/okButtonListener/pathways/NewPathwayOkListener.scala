package view.editor.okButtonListener.pathways

import controller.editor.EditorController
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects._
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.forms.pathways.NewPathway.OriginNodeIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListenerStateless
import view.editor.okButtonListener.pathways.NewPathwayOkListener.{DestinationNodeIdIndex, PathwayDescriptionIndex}
import view.form.{Form, FormBuilder, OkFormButtonListener, OkFormButtonListenerUnconditional}

object NewPathwayOkListener {

  val DestinationNodeIdIndex: Int = 0
  val PathwayDescriptionIndex: Int = 1

  private case class NewPathwayOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListenerUnconditional(form, controller) {

    override def performAction(): Unit = {
      val originNodeId = form.elements(OriginNodeIdIndex).value.toInt
      val nextForm: Form = FormBuilder()
        .addComboField(
          "Which story node is the destination node?",
          controller.nodesControls.getNodesIds(d =>
            controller.pathwaysControls.isNewPathwayValid(originNodeId, d.id)
          ).map(id => id.toString)
        )
        .addTextAreaField("What description should the pathway show?")
        .get(controller)
      nextForm.setOkButtonListener(NewPathwayNextFormOkListener(nextForm, controller, originNodeId))
      nextForm.render()
    }

  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener = NewPathwayOkListener(form, controller)
}

private case class NewPathwayNextFormOkListener(override val form: Form,
                                                override val controller: EditorController,
                                                originNodeId: Int)
  extends EditorOkFormButtonListenerStateless(form, controller) {

  override def editorControllerAction(): Unit = controller.pathwaysControls.addNewPathway(
    originNodeId,
    form.elements(DestinationNodeIdIndex).value.toInt,
    form.elements(PathwayDescriptionIndex).value
  )

  override def inputConditions: List[(Boolean, String)] =
    List((NonEmptyString(form.elements(PathwayDescriptionIndex).value), mustBeSpecified(TheDescription)))
}
