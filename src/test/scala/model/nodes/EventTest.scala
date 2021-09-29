package model.nodes

import mock.MockFactory.CharacterFactory
import mock.MockFactory.StatModifierFactory
import mock.MockFactory.ItemFactory
import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.StatModifier
import model.characters.properties.stats.StatName
import model.items.Item
import specs.{FlatTestSpec, SerializableSpec}

class EventTest extends FlatTestSpec with SerializableSpec{

  val statModifier: StatModifier = StatModifierFactory.statModifier(StatName.Intelligence)
  val statEvent: StatEvent = StatEvent("stat-event-description", statModifier)

  val item: Item = ItemFactory.mockKeyItem
  val itemEvent: ItemEvent = ItemEvent("item-event-description", item)

  val player: Player = CharacterFactory.mockPlayer(1)

  val storyModel: StoryModel =
    StoryModel("s", player, StoryNode(0, "narrative", None, Set(), List(statEvent, itemEvent)))

  "The StatEvent" should "have a description" in {
    itemEvent.description shouldEqual "item-event-description"
  }

  it should "put a StatModifier into the player when executed" in {
    statEvent(storyModel)
    player.properties.statModifiers.contains(statModifier) shouldEqual true
  }

  "The ItemEvent" should "have a description" in {
    statEvent.description shouldEqual "stat-event-description"
  }

  it should "put an Item into the player's inventory when executed" in {
    itemEvent(storyModel)
    storyModel.player.inventory.contains(item) shouldEqual true
  }

  it should behave like serializationTest(statEvent)

  it should behave like serializationTest(itemEvent)

}
