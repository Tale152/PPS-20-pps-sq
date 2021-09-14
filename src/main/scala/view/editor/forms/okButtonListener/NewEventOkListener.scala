package view.editor.forms.okButtonListener

import controller.editor.EditorController
import model.characters.Character
import model.characters.properties.stats.StatName.StatName
import model.characters.properties.stats.{StatModifier, StatName}
import model.items.{ConsumableItem, EquipItem, KeyItem}
import model.nodes.{ItemEvent, StatEvent}
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects.{
  TheDescription, TheName, TheNarrative, TheValue
}
import view.editor.forms.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.forms.NewEvent
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

private case class NewStatModifierOkListener(override val form: Form,
                                     nodeId: Int,
                                     override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  //match requires stable identifiers
  private val WisdomString: String = StatName.Wisdom.toString
  private val CharismaString: String = StatName.Charisma.toString
  private val StrengthString: String = StatName.Strength.toString
  private val DexterityString: String = StatName.Dexterity.toString
  private val IntelligenceString: String = StatName.Intelligence.toString
  private val ConstitutionString: String = StatName.Constitution.toString

  override def editorControllerAction(): Unit = {
    controller.addEventToNode(
      nodeId,
      StatEvent(form.elements(3).value,
        StatModifier(
          getSelectedStatName(form.elements.head.value),
          getStatModifierStrategy(
            form.elements(1).value,
            form.elements(2).value.toInt
          )
        )
      )
    )
  }

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements(3).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(2).value), mustBeSpecified(TheValue))
  )

  override def stateConditions: List[(Boolean, String)] = List()

  private def getSelectedStatName(statNameStr: String): StatName = statNameStr match {
    case WisdomString => StatName.Wisdom
    case CharismaString => StatName.Charisma
    case StrengthString => StatName.Strength
    case DexterityString => StatName.Dexterity
    case IntelligenceString => StatName.Intelligence
    case ConstitutionString => StatName.Constitution
  }

  private def getStatModifierStrategy(selectedStrategyStr: String, value: Int): Int => Int = selectedStrategyStr match {
    case NewEventOkListener.IncrementOption => v => v + value
    case NewEventOkListener.DecrementOption => v => v - value
  }

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
    case EquipItemString => println("TODO implement")
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
      .addComboField("Effect on health", List("Increase", "Decrease"))
      .addIntegerField("Value")
      .addTextAreaField("Narrative upon finding the item")
      .get(controller)
    form.setOkButtonListener(NewConsumableItemOkListener(form, nodeId, controller))
    form.render()
  }

  override def inputConditions: List[(Boolean, String)] = List()

  override def stateConditions: List[(Boolean, String)] = List()
}

private case class NewKeyItemOkListener(override val form: Form,
                                             nodeId: Int,
                                             override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit = controller.addEventToNode(nodeId, ItemEvent(
    form.elements(2).value,
    KeyItem(form.elements.head.value, form.elements(1).value)
  ))

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements.head.value), mustBeSpecified(TheName)),
    (NonEmptyString(form.elements(1).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(2).value), mustBeSpecified(TheNarrative))
  )

  override def stateConditions: List[(Boolean, String)] = List()
}

private case class NewConsumableItemOkListener(override val form: Form,
                                        nodeId: Int,
                                        override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit = {

    def getConsumableStrategy(selectedStrategyStr: String, value: Int): Character => Unit = selectedStrategyStr match {
      case "Increase" => c => c.properties.health.currentPS = c.properties.health.currentPS + value
      case "Decrease" => c => c.properties.health.currentPS = c.properties.health.currentPS - value
    }

    controller.addEventToNode(nodeId, ItemEvent(
      form.elements(4).value,
      ConsumableItem(
        form.elements.head.value,
        form.elements(1).value,
        getConsumableStrategy(form.elements(2).value, form.elements(3).value.toInt))
    ))
  }

  override def inputConditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements.head.value), mustBeSpecified(TheName)),
    (NonEmptyString(form.elements(1).value), mustBeSpecified(TheDescription)),
    (NonEmptyString(form.elements(3).value), mustBeSpecified(TheValue)),
    (NonEmptyString(form.elements(4).value), mustBeSpecified(TheNarrative))
  )

  override def stateConditions: List[(Boolean, String)] = List()
}