package view.editor.forms

import controller.editor.EditorController
import model.characters.properties.stats.StatName
import view.editor.forms.okButtonListener.AddStatModifierOkListener
import view.form.{Form, FormBuilder}

object AddStatModifier {

  val IncrementOption: String = "Increment (+)"
  val DecrementOption: String = "Decrement (-)"

  val SelectedNodeIndex = 0
  val AffectedStatIndex = 1
  val SelectedStrategyIndex = 2
  val ValueIndex = 3
  val DescriptionIndex = 4


  def showAddStatModifierForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("To which node you want to want to insert a stat modifier?")
      .addComboField("Which stat you want to affect?", List(
        StatName.Charisma.toString,
        StatName.Constitution.toString,
        StatName.Dexterity.toString,
        StatName.Intelligence.toString,
        StatName.Strength.toString,
        StatName.Wisdom.toString
      ))
      .addComboField("What you want to do with the selected stat?", List(IncrementOption, DecrementOption))
      .addIntegerField("Insert the value")
      .addTextAreaField("Insert a description for the stat modifier")
      .get(editorController)
    form.setOkButtonListener(AddStatModifierOkListener(form, editorController))
    form.render()
  }
}
