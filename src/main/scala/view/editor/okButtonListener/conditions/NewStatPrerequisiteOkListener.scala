package view.editor.okButtonListener.conditions

import controller.editor.EditorController
import model.StoryModel
import model.characters.properties.stats.StatName
import model.characters.properties.stats.StatName.StatName
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.editor.okButtonListener.conditions.NewPathwayPrerequisiteNextFormOkListener._
import view.form.Form
import view.editor.util.StatsNameStringUtil._

case class NewStatPrerequisiteOkListener(override val form: Form,
                                         override val controller: EditorController,
                                         originNodeId: Int,
                                         destinationNodeId: Int) extends EditorOkFormButtonListener(form, controller) {

  private def getPrerequisite(selectedPrerequisiteStr: String, requiredValue: Int): StoryModel => Boolean = {

    def strToStatName(selectedPrerequisiteStr: String): StatName = selectedPrerequisiteStr match {
      case WisdomString => StatName.Wisdom
      case CharismaString => StatName.Charisma
      case StrengthString => StatName.Strength
      case DexterityString => StatName.Dexterity
      case IntelligenceString => StatName.Intelligence
      case ConstitutionString => StatName.Constitution
    }

    m => m.player.properties.stat(strToStatName(selectedPrerequisiteStr)).value >= requiredValue
  }

  override def editorControllerAction(): Unit = controller.addPrerequisiteToPathway(
    originNodeId, destinationNodeId, getPrerequisite(
      form.elements(StatValueFormStatIndex).value, form.elements(StatValueFormValueIndex).value.toInt
    )
  )

  override def inputConditions: List[(Boolean, String)] = List()

  override def stateConditions: List[(Boolean, String)] = List()
}
