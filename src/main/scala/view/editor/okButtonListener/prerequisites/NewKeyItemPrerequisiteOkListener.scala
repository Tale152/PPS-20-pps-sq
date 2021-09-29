package view.editor.okButtonListener.prerequisites

import controller.editor.EditorController
import model.nodes.ItemPrerequisite
import view.editor.okButtonListener.EditorOkFormButtonListenerUnconditional
import view.editor.okButtonListener.prerequisites.NewPathwayPrerequisiteNextFormOkListener.KeyItemFormItemIndex
import view.editor.util.IndexedComboListUtil.extractIndexFromOption
import view.form.Form

case class NewKeyItemPrerequisiteOkListener(override val form: Form,
                                            override val controller: EditorController,
                                            originNodeId: Int,
                                            destinationNodeId: Int)
  extends EditorOkFormButtonListenerUnconditional(form, controller) {

  override def editorControllerAction(): Unit = {
    val keyItem = controller
      .nodesControls.getAllKeyItemsBeforeNode(controller.nodesControls.getStoryNode(originNodeId).get)(
        extractIndexFromOption(form.elements(KeyItemFormItemIndex).value)
      )
    controller.pathwaysControls.addPrerequisiteToPathway(originNodeId, destinationNodeId, ItemPrerequisite(keyItem))
  }

}
