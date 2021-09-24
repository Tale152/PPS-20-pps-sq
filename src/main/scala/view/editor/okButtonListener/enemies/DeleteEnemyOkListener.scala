package view.editor.okButtonListener.enemies

import controller.editor.EditorController
import view.editor.forms.enemies.DeleteEnemy.StoryNodeIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListenerUnconditional
import view.form.Form

case class DeleteEnemyOkListener(override val form: Form,
                                 override val controller: EditorController)
  extends EditorOkFormButtonListenerUnconditional(form, controller) {

  override def editorControllerAction(): Unit =
    controller.nodesControls.deleteEnemyFromNode(form.elements(StoryNodeIdIndex).value.toInt)
}
