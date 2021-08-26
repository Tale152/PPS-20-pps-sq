package model.nodes

import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.{Stat, StatModifier, StatName}
import model.items.{Item, KeyItem}
import specs.{FlatTestSpec, SerializableSpec}

class EventTest extends FlatTestSpec with SerializableSpec{

  val statModifier: StatModifier = StatModifier(StatName.Intelligence, v => v + 1)
  val statEvent: Event = StatEvent(statModifier)

  val item: Item = KeyItem("tester", "an item for testing purpose")
  val itemEvent: Event = ItemEvent(item)

  val player: Player = Player("player", 1, Set(Stat(1, StatName.Intelligence)))
  val storyModel: StoryModel =
    StoryModel(player, StoryNode(0, "narrative", Set(), List(statEvent.execute, itemEvent.execute)))

  "The StatEvent, when executed," should "put a StatModifier into the player" in {
    statEvent.execute(storyModel)
    player.properties.statModifiers.contains(statModifier) shouldEqual true
  }

  "The ItemEvent, when executed," should "put an Item into the player's inventory" in {
    itemEvent.execute(storyModel)
    storyModel.player.inventory.contains(item) shouldEqual true
  }

  it should behave like serializationTest(statEvent)

  it should behave like serializationTest(itemEvent)
}
