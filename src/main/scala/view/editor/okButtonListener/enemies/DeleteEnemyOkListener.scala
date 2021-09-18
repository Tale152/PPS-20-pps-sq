package view.editor.okButtonListener.enemies

import controller.editor.EditorController
import view.editor.forms.enemies.DeleteEnemy.StoryNodeIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.form.Form

case class DeleteEnemyOkListener(override val form: Form,
                                 override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit =
    controller.nodesControls.deleteEnemyFromNode(form.elements(StoryNodeIdIndex).value.toInt)

  override def inputConditions: List[(Boolean, String)] = List()

  override def stateConditions: List[(Boolean, String)] = List()
}
