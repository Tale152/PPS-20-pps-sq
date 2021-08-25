package model.nodes

import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.{Stat, StatModifier, StatName}
import model.nodes.StatEvent.StatEvent
import specs.FlatTestSpec

class EventTest extends FlatTestSpec {

  val statModifier: StatModifier = StatModifier(StatName.Intelligence, v => v + 1)
  val statEvent: StatEvent = StatEvent(statModifier)
  val player: Player = Player("player", 1, Set(Stat(1, StatName.Intelligence)))
  val storyModel: StoryModel = StoryModel(player, StoryNode(0, "narrative", Set(), List(statEvent.execute)))

  "The StatEvent, when executed," should "put a StatModifier into the player" in {
    statEvent.execute(storyModel)
    player.properties.statModifiers shouldEqual Set(statModifier)
  }
}
