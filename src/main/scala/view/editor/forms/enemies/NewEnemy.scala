package view.editor.forms.enemies

import controller.editor.EditorController
import view.editor.okButtonListener.enemies.NewEnemyOkListener
import view.form.{Form, FormBuilder}

object NewEnemy {

  private val DefaultStatValue: Integer = 10

  def showNewEnemyForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "In which node you want to want to insert an enemy? (id)",
        editorController.getNodesIds(n => n.enemy.isEmpty).map(n => n.toString)
      )
      .addTextField("Enemy name")
      .addSpinnerNumberField("Health", DefaultStatValue)
      .addSpinnerNumberField("Charisma", DefaultStatValue)
      .addSpinnerNumberField("Constitution", DefaultStatValue)
      .addSpinnerNumberField("Dexterity", DefaultStatValue)
      .addSpinnerNumberField("Intelligence", DefaultStatValue)
      .addSpinnerNumberField("Strength", DefaultStatValue)
      .addSpinnerNumberField("Wisdom", DefaultStatValue)
      .get(editorController)
    form.setOkButtonListener(NewEnemyOkListener(form, editorController))
    form.render()
  }
}
