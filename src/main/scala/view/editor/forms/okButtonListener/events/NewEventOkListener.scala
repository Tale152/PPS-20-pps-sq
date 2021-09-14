package view.editor.forms.okButtonListener.events

import controller.editor.EditorController
import model.characters.properties.stats.StatName._
import model.items.{ConsumableItem, EquipItem, KeyItem}
import view.editor.forms.NewEvent
import view.editor.forms.okButtonListener.events.NewEventOkListener.{DecrementOption, IncrementOption}
import view.editor.forms.okButtonListener.events.items.{
  EquipItemUtil, NewConsumableItemOkListener, NewEquipItemOkListener, NewKeyItemOkListener
}
import view.editor.forms.okButtonListener.events.statModifiers.NewStatModifierOkListener
import view.form.{Form, FormBuilder, OkFormButtonListener}

object NewEventOkListener {

  val IncrementOption: String = "Increment (+)"
  val DecrementOption: String = "Decrement (-)"

  private case class NewEventOkListener(override val form: Form, override val controller: EditorController)
    extends OkFormButtonListener(form, controller) {

    private def showItemCategoryForm(nodeId: Int): Unit = {
      val form: Form = FormBuilder()
        .addComboField("Select the category of the new item", List(
          KeyItem.toString,
          ConsumableItem.toString,
          EquipItem.toString
        ))
        .get(controller)
      form.setOkButtonListener(NewItemCategoryOkListener(form, nodeId, controller))
      form.render()
    }

    private def showStatModifierForm(nodeId: Int): Unit = {
      val form: Form = FormBuilder()
        .addComboField("Which stat you want to affect?", List(
          Charisma.toString,
          Constitution.toString,
          Dexterity.toString,
          Intelligence.toString,
          Strength.toString,
          Wisdom.toString
        ))
        .addComboField("What you want to do with the selected stat?", List(IncrementOption, DecrementOption))
        .addIntegerField("Insert the value")
        .addTextAreaField("Insert a description for the stat modifier")
        .get(controller)
      form.setOkButtonListener(NewStatModifierOkListener(form, nodeId, controller))
      form.render()
    }

    override def performAction(): Unit = form.elements(1).value match {
      case NewEvent.StatModifierOption => showStatModifierForm(form.elements.head.value.toInt)
      case NewEvent.ItemOption => showItemCategoryForm(form.elements.head.value.toInt)
    }

    override def inputConditions: List[(Boolean, String)] = List()

    override def stateConditions: List[(Boolean, String)] = List()

  }

  def apply(form: Form, controller: EditorController): OkFormButtonListener = NewEventOkListener(form, controller)
}

private case class NewItemCategoryOkListener(override val form: Form,
                                             nodeId: Int,
                                             override val controller: EditorController)
  extends OkFormButtonListener(form, controller) {

  private val KeyItemString: String = KeyItem.toString
  private val ConsumableItemString: String = ConsumableItem.toString
  private val EquipItemString: String = EquipItem.toString

  override def performAction(): Unit = form.elements.head.value match {
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
      .addComboField("Effect on health", List(IncrementOption, DecrementOption))
      .addIntegerField("Value")
      .addTextAreaField("Narrative upon finding the item")
      .get(controller)
    form.setOkButtonListener(NewConsumableItemOkListener(form, nodeId, controller))
    form.render()
  }

  private def showEquipItemForm(nodeId: Int): Unit = {
    val form: Form = FormBuilder()
      .addTextField("Item name")
      .addTextAreaField("Item description")
      .addComboField("Type", EquipItemUtil.ItemTypeStrings)
      .addTextAreaField("Narrative upon finding the item")
      .addComboField("Effect on Charisma", List(IncrementOption, DecrementOption))
      .addIntegerField("Value (Charisma)")
      .addComboField("Effect on Constitution", List(IncrementOption, DecrementOption))
      .addIntegerField("Value (Constitution)")
      .addComboField("Effect on Dexterity", List(IncrementOption, DecrementOption))
      .addIntegerField("Value (Dexterity)")
      .addComboField("Effect on Intelligence", List(IncrementOption, DecrementOption))
      .addIntegerField("Value (Intelligence)")
      .addComboField("Effect on Strength", List(IncrementOption, DecrementOption))
      .addIntegerField("Value (Strength)")
      .addComboField("Effect on Wisdom", List(IncrementOption, DecrementOption))
      .addIntegerField("Value (Wisdom)")
      .get(controller)
    form.setOkButtonListener(NewEquipItemOkListener(form, nodeId, controller))
    form.render()
  }

  override def inputConditions: List[(Boolean, String)] = List()

  override def stateConditions: List[(Boolean, String)] = List()
}
