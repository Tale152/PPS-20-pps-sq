package view.editor.okButtonListener.prerequisites

import controller.editor.EditorController
import model.characters.properties.stats.StatName
import model.characters.properties.stats.StatName.StatName
import model.nodes.{Prerequisite, StatPrerequisite}
import view.editor.okButtonListener.EditorOkFormButtonListenerUnconditional
import view.editor.okButtonListener.prerequisites.NewPathwayPrerequisiteNextFormOkListener._
import view.editor.util.StatsNameStringUtil._
import view.form.Form

case class NewStatPrerequisiteOkListener(override val form: Form,
                                         override val controller: EditorController,
                                         originNodeId: Int,
                                         destinationNodeId: Int)
  extends EditorOkFormButtonListenerUnconditional(form, controller) {

  private def prerequisite(selectedPrerequisiteStr: String, requiredValue: Int): Prerequisite = {

    def strToStatName(selectedPrerequisiteStr: String): StatName = selectedPrerequisiteStr match {
      case WisdomString => StatName.Wisdom
      case CharismaString => StatName.Charisma
      case StrengthString => StatName.Strength
      case DexterityString => StatName.Dexterity
      case IntelligenceString => StatName.Intelligence
      case ConstitutionString => StatName.Constitution
    }

    StatPrerequisite(strToStatName(selectedPrerequisiteStr), requiredValue)
  }

  override def editorControllerAction(): Unit = controller.pathwaysControls.addPrerequisiteToPathway(
    originNodeId, destinationNodeId, prerequisite(
      form.elements(StatValueFormStatIndex).value, form.elements(StatValueFormValueIndex).value.toInt
    )
  )

}
