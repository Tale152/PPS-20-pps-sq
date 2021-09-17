package view.editor.okButtonListener.events.items

import controller.editor.EditorController
import view.editor.okButtonListener.events.NewEventOkListener.{ConsumableItemString, EquipItemString, ItemFormCategoryIndex, KeyItemString}
import view.editor.util.EquipItemTypeUtil
import view.editor.util.OperationStringUtil.IncrementDecrementOptions
import view.form.{Form, FormBuilder, OkFormButtonListener}

object NewItemCategoryOkListener {

  val ItemNameIndex: Int = 0
  val ItemDescriptionIndex: Int = 1
  val ItemRetrieveNarrativeIndex: Int = 2

  val ConsumableEffectHealthIndex: Int = 3
  val consumableHealthValueIndex: Int = 4

  val EquipTypeIndex: Int = 3
  val EquipEffectCharismaIndex: Int = 4
  val EquipCharismaValueIndex: Int = 5
  val EquipEffectConstitutionIndex: Int = 6
  val EquipConstitutionValueIndex: Int = 7
  val EquipEffectDexterityIndex: Int = 8
  val EquipDexterityValueIndex: Int = 9
  val EquipEffectIntelligenceIndex: Int = 10
  val EquipIntelligenceValueIndex: Int = 11
  val EquipEffectStrengthIndex: Int = 12
  val EquipStrengthValueIndex: Int = 13
  val EquipEffectWisdomIndex: Int = 14
  val EquipWisdomValueIndex: Int = 15

  private case class NewItemCategoryOkListener(override val form: Form,
                                               nodeId: Int,
                                               override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    override def performAction(): Unit = form.elements(ItemFormCategoryIndex).value match {
      case KeyItemString => showKeyItemForm(nodeId)
      case ConsumableItemString => showConsumableItemForm(nodeId)
      case EquipItemString => showEquipItemForm(nodeId)
    }

    private def showKeyItemForm(nodeId: Int): Unit = {
      val form: Form = FormBuilder()
        .addTextField("Item name")
        .addTextAreaField("Item description")
        .addTextAreaField("Narrative upon finding the item")
        .get(controller)
      form.setOkButtonListener(NewKeyItemOkListener(form, nodeId, controller))
      form.render()
    }

    private def showConsumableItemForm(nodeId: Int): Unit = {
      val form: Form = FormBuilder()
        .addTextField("Item name")
        .addTextAreaField("Item description")
        .addTextAreaField("Narrative upon finding the item")
        .addComboField("Effect on health", IncrementDecrementOptions)
        .addSpinnerNumberField("Value")
        .get(controller)
      form.setOkButtonListener(NewConsumableItemOkListener(form, nodeId, controller))
      form.render()
    }

    private def showEquipItemForm(nodeId: Int): Unit = {
      val form: Form = FormBuilder()
        .addTextField("Item name")
        .addTextAreaField("Item description")
        .addTextAreaField("Narrative upon finding the item")
        .addComboField("Type", EquipItemTypeUtil.ItemTypeStrings)
        .addComboField("Effect on Charisma", IncrementDecrementOptions)
        .addSpinnerNumberField("Value (Charisma)")
        .addComboField("Effect on Constitution", IncrementDecrementOptions)
        .addSpinnerNumberField("Value (Constitution)")
        .addComboField("Effect on Dexterity", IncrementDecrementOptions)
        .addSpinnerNumberField("Value (Dexterity)")
        .addComboField("Effect on Intelligence", IncrementDecrementOptions)
        .addSpinnerNumberField("Value (Intelligence)")
        .addComboField("Effect on Strength", IncrementDecrementOptions)
        .addSpinnerNumberField("Value (Strength)")
        .addComboField("Effect on Wisdom", IncrementDecrementOptions)
        .addSpinnerNumberField("Value (Wisdom)")
        .get(controller)
      form.setOkButtonListener(NewEquipItemOkListener(form, nodeId, controller))
      form.render()
    }

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List()
  }

  def apply(form: Form, nodeId: Int, controller: EditorController): OkFormButtonListener =
    NewItemCategoryOkListener(form, nodeId, controller)
}
