package view.editor.forms.enemies

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.okButtonListener.enemies.NewEnemyOkListener
import view.form.{Form, FormBuilder}

object NewEnemy {

  private val DefaultStatValue: Integer = 10

  val NodeIdIndex = 0
  val EnemyNameIndex = 1
  val EnemyHealthIndex = 2
  val CharismaIndex = 3
  val ConstitutionIndex = 4
  val DexterityIndex = 5
  val IntelligenceIndex = 6
  val StrengthIndex = 7
  val WisdomIndex = 8

  def showNewEnemyForm(editorController: EditorController): Unit = {
    val targetNodes = editorController.getNodesIds(n => n.enemy.isEmpty).map(n => n.toString)
    if(targetNodes.nonEmpty){
      val form: Form = FormBuilder()
        .addComboField("In which node you want to want to insert an enemy? (id)",targetNodes)
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
    } else {
      EditorView.showForbiddenActionDialog("There are no nodes without an enemy")
    }

  }
}
