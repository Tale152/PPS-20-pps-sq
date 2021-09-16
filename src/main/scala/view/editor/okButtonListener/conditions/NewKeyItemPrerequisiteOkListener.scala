package view.editor.okButtonListener.conditions

import controller.editor.EditorController
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.form.Form

case class NewKeyItemPrerequisiteOkListener(override val form: Form,
                                            override val controller: EditorController,
                                            originNodeId: Int,
                                            destinationNodeId: Int)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit = {
    val keyItem = controller.getAllKeyItemsBeforeNode(controller.getStoryNode(originNodeId).get)(
      form.elements.head.value.split("]")(0).replace("[", "").toInt - 1
    )
    controller.addPrerequisiteToPathway(
      originNodeId,
      destinationNodeId,
      m => m.player.inventory.contains(keyItem)
    )
  }

  override def inputConditions: List[(Boolean, String)] = List()

  override def stateConditions: List[(Boolean, String)] = List()
}
