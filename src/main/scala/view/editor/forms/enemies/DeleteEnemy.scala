package view.editor.forms.enemies

import controller.editor.EditorController
import view.editor.okButtonListener.enemies.DeleteEnemyOkListener
import view.form.{Form, FormBuilder}

object DeleteEnemy {

  def showDeleteEnemyForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "From what node you want to delete an existing enemy? (id)",
        editorController.getNodesIds(n => n.enemy.nonEmpty).map(n => n.toString)
      )
      .get(editorController)
    form.setOkButtonListener(DeleteEnemyOkListener(form, editorController))
    form.render()
  }
}
