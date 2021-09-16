package view.editor.okButtonListener.events

import controller.editor.EditorController
import model.items.{ConsumableItem, EquipItem, KeyItem}
import view.editor.forms.events.NewEvent.{EventGenreIndex, ItemOption, NodeIdIndex, StatModifierOption}
import view.editor.okButtonListener.events.items.NewItemCategoryOkListener
import view.editor.okButtonListener.events.statModifiers.NewStatModifierOkListener
import view.editor.util.OperationStringUtil.IncrementDecrementOptions
import view.editor.util.StatsNameStringUtil.StatStrings
import view.form.{Form, FormBuilder, OkFormButtonListener}

object NewEventOkListener {

  val KeyItemString: String = KeyItem.toString
  val ConsumableItemString: String = ConsumableItem.toString
  val EquipItemString: String = EquipItem.toString

  val ItemFormCategoryIndex: Int = 0

  val StatModifierFormStatIndex: Int = 0
  val StatModifierIncDecIndex: Int = 1
  val StatModifierValueIndex: Int = 2
  val StatModifierDescriptionIndex: Int = 3

  private case class NewEventOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    private def showItemCategoryForm(nodeId: Int): Unit = {
      val nextForm: Form = FormBuilder()
        .addComboField(
          "Select the category of the new item",
          List(KeyItemString, ConsumableItemString, EquipItemString)
        )
        .get(controller)
      nextForm.setOkButtonListener(NewItemCategoryOkListener(nextForm, nodeId, controller))
      nextForm.render()
    }

    private def showStatModifierForm(nodeId: Int): Unit = {
      val nextForm: Form = FormBuilder()
        .addComboField("Which stat you want to affect?", StatStrings)
        .addComboField("What you want to do with the selected stat?", IncrementDecrementOptions)
        .addSpinnerNumberField("Insert the value")
        .addTextAreaField("Insert a description for the stat modifier")
        .get(controller)
      nextForm.setOkButtonListener(NewStatModifierOkListener(nextForm, nodeId, controller))
      nextForm.render()
    }

    override def performAction(): Unit = form.elements(EventGenreIndex).value match {
      case StatModifierOption => showStatModifierForm(form.elements(NodeIdIndex).value.toInt)
      case ItemOption => showItemCategoryForm(form.elements(NodeIdIndex).value.toInt)
    }

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List()

  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener = NewEventOkListener(form, controller)
}
