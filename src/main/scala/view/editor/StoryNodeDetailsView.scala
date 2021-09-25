package view.editor

import controller.editor.EditorController
import model.characters.Enemy
import model.characters.properties.stats.StatName.{Charisma, Constitution, Dexterity, Intelligence, Strength, Wisdom}
import model.items.{ConsumableItem, EquipItem, Item, KeyItem}
import model.nodes.{ItemEvent, StatEvent, StoryNode}
import view.AbstractView
import view.util.common.{ControlsPanel, Scrollable}
import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqTextArea}

import java.awt.BorderLayout
import javax.swing.BoxLayout

case class StoryNodeDetailsView(private val storyNode: StoryNode, private val editorController: EditorController)
  extends AbstractView {

  this.setLayout(new BorderLayout())
  private val centerPanel = new SqSwingBoxPanel(BoxLayout.Y_AXIS) {}

  override def populateView(): Unit = {
    centerPanel.add(SqTextArea(
      "ID: " + storyNode.id +
        "\n\nNarrative:\n" + storyNode.narrative +
        "\n\nNumber of nodes reaching this node: " +
        editorController.pathwaysControls.getAllOriginNodes(storyNode.id).size +
        "\nNumber of nodes reached by this node: " + storyNode.pathways.size
    ))
    if (storyNode.enemy.nonEmpty) {
      centerPanel.add(SqTextArea(enemyString(storyNode.enemy.get)))
    }
    if (storyNode.events.nonEmpty) {
      var str = "Events:\n"
      storyNode.events.foreach {
        case statEvent: StatEvent => str = str + "\n" + statEventString(statEvent) + "\n"
        case itemEvent: ItemEvent => str = str + "\n" + itemEventString(itemEvent) + "\n"
      }
      centerPanel.add(SqTextArea(str))
    }
    this.add(Scrollable(centerPanel), BorderLayout.CENTER)
    this.add(ControlsPanel(List(("q", ("[Q] Quit", _ => editorController.execute())))), BorderLayout.SOUTH)
  }

  private def enemyString(enemy: Enemy): String = {
    "Enemy: " + enemy.name +
      "\nHealth: " + enemy.maxPS +
      "\nCharisma: " + enemy.properties.stat(Charisma).value +
      "\nConstitution: " + enemy.properties.stat(Constitution).value +
      "\nDexterity: " + enemy.properties.stat(Dexterity).value +
      "\nIntelligence: " + enemy.properties.stat(Intelligence).value +
      "\nStrength: " + enemy.properties.stat(Strength).value +
      "\nWisdom: " + enemy.properties.stat(Wisdom).value
  }

  private def itemEventString(itemEvent: ItemEvent): String = itemEvent.item match {
    case keyItem: KeyItem => keyItemEventString(keyItem, itemEvent)
    case equipItem: EquipItem => equipItemEventString(equipItem, itemEvent)
    case consumableItem: ConsumableItem => consumableItemEventString(consumableItem, itemEvent)
    case item: Item => item.toString
  }

  private def itemTypeString(item: Item): String = item match {
    case _: KeyItem => "Key Item"
    case _: EquipItem => "Equip Item"
    case _: ConsumableItem => "Consumable Item"
    case item: Item => item.toString
  }

  private def itemEventString(item: Item, itemEvent: ItemEvent, additionalItemText: String = ""): String = {
    itemTypeString(item) + ":" +
      "\nName:\n" + item.name +
      "\nDescription:\n" + item.description +
      additionalItemText +
      "\nNarrative upon finding the item:\n" + itemEvent.description
  }

  private def keyItemEventString(keyItem: KeyItem, itemEvent: ItemEvent): String =
    itemEventString(keyItem, itemEvent)

  private def equipItemEventString(equipItem: EquipItem, itemEvent: ItemEvent): String = {
    val equipItemAdditionalText = "\nType: " + equipItem.equipItemType.toString +
      "\nCharisma: " + equipItem.statModifiers.filter(m => m.statName == Charisma).head.modifyStrategy(0) +
      "\nConstitution: " + equipItem.statModifiers.filter(m => m.statName == Constitution).head.modifyStrategy(0) +
      "\nDexterity: " + equipItem.statModifiers.filter(m => m.statName == Dexterity).head.modifyStrategy(0) +
      "\nIntelligence: " + equipItem.statModifiers.filter(m => m.statName == Intelligence).head.modifyStrategy(0) +
      "\nStrength: " + equipItem.statModifiers.filter(m => m.statName == Strength).head.modifyStrategy(0) +
      "\nWisdom: " + equipItem.statModifiers.filter(m => m.statName == Wisdom).head.modifyStrategy(0)
    itemEventString(equipItem, itemEvent, equipItemAdditionalText)
  }

  private def consumableItemEventString(consumableItem: ConsumableItem, itemEvent: ItemEvent): String =
    itemEventString(consumableItem, itemEvent)

  private def statEventString(statEvent: StatEvent): String =
    "Stat modifier:" +
      "\nEffect: " + statEvent.statModifier.statName.toString + " " + statEvent.statModifier.modifyStrategy(0) +
      "\nDescription:\n" + statEvent.description

}
