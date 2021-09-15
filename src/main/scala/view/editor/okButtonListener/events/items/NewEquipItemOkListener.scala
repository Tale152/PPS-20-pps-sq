package view.editor.okButtonListener.events.items

import controller.editor.EditorController
import model.characters.properties.stats.StatModifier
import model.characters.properties.stats.StatName._
import model.items.EquipItem
import model.items.EquipItemType._
import model.nodes.ItemEvent
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.{TheDescription, TheName, TheNarrative}
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.editor.okButtonListener.events.NewEventOkListener.{DecrementOption, IncrementOption}
import view.editor.okButtonListener.events.items.EquipItemUtil._
import view.form.Form

case class NewEquipItemOkListener(override val form: Form,
                                  nodeId: Int,
                                  override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit = {

    def getModifierStrategy(selectedStrategyStr: String, value: Int): Int => Int = selectedStrategyStr match {
      case IncrementOption => v => v + value
      case DecrementOption => v => v - value
    }

    controller.addEventToNode(nodeId, ItemEvent(
      form.elements(3).value,
      EquipItem(
        form.elements.head.value,
        form.elements(1).value,
        Set(
          StatModifier(Charisma,getModifierStrategy(
            form.elements(CharismaStrategyIndex).value, form.elements(CharismaValueIndex).value.toInt)),
          StatModifier(Constitution,getModifierStrategy(
            form.elements(ConstitutionStrategyIndex).value, form.elements(ConstitutionValueIndex).value.toInt)),
          StatModifier(Dexterity,getModifierStrategy(
            form.elements(DexterityStrategyIndex).value, form.elements(DexterityValueIndex).value.toInt)),
          StatModifier(Intelligence,getModifierStrategy(
            form.elements(IntelligenceStrategyIndex).value, form.elements(IntelligenceValueIndex).value.toInt)),
          StatModifier(Strength,getModifierStrategy(
            form.elements(StrengthStrategyIndex).value, form.elements(StrengthValueIndex).value.toInt)),
          StatModifier(Wisdom,getModifierStrategy(
            form.elements(WisdomStrategyIndex).value, form.elements(WisdomValueIndex).value.toInt))
        ),
        getEquipItemType(form.elements(2).value)
      )
    ))
  }

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements(ItemNameIndex).value), mustBeSpecified(TheName)),
    (NonEmptyString(form.elements(ItemDescriptionIndex).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(ItemNarrativeIndex).value), mustBeSpecified(TheNarrative)),
    (NonEmptyString(form.elements(CharismaValueIndex).value), mustBeSpecified(Charisma.toString)),
    (NonEmptyString(form.elements(ConstitutionValueIndex).value), mustBeSpecified(Constitution.toString)),
    (NonEmptyString(form.elements(DexterityValueIndex).value), mustBeSpecified(Dexterity.toString)),
    (NonEmptyString(form.elements(IntelligenceValueIndex).value), mustBeSpecified(Intelligence.toString)),
    (NonEmptyString(form.elements(StrengthValueIndex).value), mustBeSpecified(Strength.toString)),
    (NonEmptyString(form.elements(WisdomValueIndex).value), mustBeSpecified(Wisdom.toString))
  )

  override def stateConditions: List[(Boolean, String)] = List()
}

object EquipItemUtil {

  val BootsString: String = Boots.toString
  val ArmorString: String = Armor.toString
  val SocksString: String = Socks.toString
  val WeaponString: String = Weapon.toString
  val GlovesString: String = Gloves.toString
  val NecklaceString: String = Necklace.toString
  val HelmetString: String = Helmet.toString

  val ItemNameIndex: Int = 0
  val ItemDescriptionIndex: Int = 1
  val ItemNarrativeIndex: Int = 3
  val CharismaStrategyIndex: Int = 4
  val CharismaValueIndex: Int = 5
  val ConstitutionStrategyIndex: Int = 6
  val ConstitutionValueIndex: Int = 7
  val DexterityStrategyIndex: Int = 8
  val DexterityValueIndex: Int = 9
  val IntelligenceStrategyIndex: Int = 10
  val IntelligenceValueIndex: Int = 11
  val StrengthStrategyIndex: Int = 12
  val StrengthValueIndex: Int = 13
  val WisdomStrategyIndex: Int = 14
  val WisdomValueIndex: Int = 15

  def getEquipItemType(selectedEquipItemTypeStr: String): EquipItemType = selectedEquipItemTypeStr match {
    case BootsString => Boots
    case ArmorString => Armor
    case SocksString => Socks
    case WeaponString => Weapon
    case GlovesString => Gloves
    case NecklaceString => Necklace
    case HelmetString => Helmet
  }

  val ItemTypeStrings: List[String] = List(
    BootsString,
    ArmorString,
    SocksString,
    WeaponString,
    GlovesString,
    NecklaceString,
    HelmetString
  )
}
