package view.editor.okButtonListener.enemies

import controller.editor.EditorController
import model.characters.Enemy
import model.characters.properties.stats.{Stat, StatName}
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.{TheId, TheName}
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.editor.okButtonListener.enemies.NewEnemyOkListenerConst._
import view.form.Form

case class NewEnemyOkListener(override val form: Form,
                              override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller){

  override def editorControllerAction(): Unit = controller.addEnemyToNode(
    form.elements(NodeIdIndex).value.toInt,
    Enemy(
      form.elements(EnemyNameIndex).value.trim,
      form.elements(EnemyHealthIndex).value.toInt,
      Set(
        Stat(form.elements(CharismaIndex).value.toInt, StatName.Charisma),
        Stat(form.elements(ConstitutionIndex).value.toInt, StatName.Constitution),
        Stat(form.elements(DexterityIndex).value.toInt, StatName.Dexterity),
        Stat(form.elements(IntelligenceIndex).value.toInt, StatName.Intelligence),
        Stat(form.elements(StrengthIndex).value.toInt, StatName.Strength),
        Stat(form.elements(WisdomIndex).value.toInt, StatName.Wisdom)
      )
    )
  )

  override def inputConditions: List[(Boolean, String)] = List(
    (form.elements(NodeIdIndex).value != null, mustBeSpecified(TheId)),
    (NonEmptyString(form.elements(EnemyNameIndex).value), mustBeSpecified(TheName))
  )

  override def stateConditions: List[(Boolean, String)] = List()

}

private object NewEnemyOkListenerConst {

  val NodeIdIndex = 0
  val EnemyNameIndex = 1
  val EnemyHealthIndex = 2
  val CharismaIndex = 3
  val ConstitutionIndex = 4
  val DexterityIndex = 5
  val IntelligenceIndex = 6
  val StrengthIndex = 7
  val WisdomIndex = 8

}
