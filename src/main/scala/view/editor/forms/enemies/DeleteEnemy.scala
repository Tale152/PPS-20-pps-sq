package view.editor.forms.enemies

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.forms.EditorForm
import view.editor.okButtonListener.enemies.DeleteEnemyOkListener
import view.form.{Form, FormBuilder}

object DeleteEnemy {
  val StoryNodeIdIndex: Int = 0
}

/** @inheritdoc */
case class DeleteEnemy() extends EditorForm {

  override def show(editorController: EditorController): Unit = {
    val targetNodes = editorController.nodesControls.nodesIds(n => n.enemy.nonEmpty).map(n => n.toString)
    if(targetNodes.nonEmpty){
      val form: Form = FormBuilder()
        .addComboField("From what node you want to delete an existing enemy?", targetNodes)
        .get(editorController)
      form.setOkButtonListener(DeleteEnemyOkListener(form, editorController))
      form.render()
    } else {
      EditorView.showForbiddenActionDialog("There aren't existing enemies")
    }
  }

}
