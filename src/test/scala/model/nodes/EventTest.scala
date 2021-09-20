package model.nodes

import mock.MockFactory
import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.Stats.StatModifier
import model.characters.properties.stats.StatName
import model.items.{Item, KeyItem}
import specs.{FlatTestSpec, SerializableSpec}

class EventTest extends FlatTestSpec with SerializableSpec{

  val statModifier: StatModifier = StatModifier(StatName.Intelligence, v => v + 1)
  val statEvent: Event = StatEvent("stat-event-description", statModifier)

  val item: Item = KeyItem("tester", "an item for testing purpose")
  val itemEvent: Event = ItemEvent("item-event-description", item)

  val player: Player = Player("player", 1, MockFactory.mockSetOfStats())
  val storyModel: StoryModel =
    StoryModel("s", player, StoryNode(0, "narrative", None, Set(), List(statEvent, itemEvent)))

  "The Events" should "have a description" in {
    itemEvent.description shouldEqual "item-event-description"
    statEvent.description shouldEqual "stat-event-description"
  }

  "The StatEvent, when executed," should "put a StatModifier into the player" in {
    statEvent.handle(storyModel)
    player.properties.statModifiers.contains(statModifier) shouldEqual true
  }

  "The ItemEvent, when executed," should "put an Item into the player's inventory" in {
    itemEvent.handle(storyModel)
    storyModel.player.inventory.contains(item) shouldEqual true
  }

  it should behave like serializationTest(statEvent)

  it should behave like serializationTest(itemEvent)
}
