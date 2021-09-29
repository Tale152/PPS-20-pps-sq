package model.nodes.util

import mock.MockFactory.CharacterFactory
import mock.MockFactory.ItemFactory
import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.StatName
import model.items.KeyItem
import model.nodes.StoryNode
import specs.{FlatTestSpec, SerializableSpec}

class PrerequisiteTest extends FlatTestSpec with SerializableSpec {

  val keyItem: KeyItem = ItemFactory.mockKeyItem
  val player: Player = CharacterFactory.mockPlayer(1)
  player.inventory = player.inventory :+ keyItem

  val storyModel: StoryModel = StoryModel("story", player, StoryNode(0, "narrative", None, Set(), List()))

  val itemPrerequisite: ItemPrerequisite = ItemPrerequisite(keyItem)
  val statPrerequisite: StatPrerequisite = StatPrerequisite(StatName.Intelligence, 1)

  "An ItemPrerequisite" should "be true if satisfied" in {
    itemPrerequisite(storyModel) shouldEqual true
  }

  "An ItemPrerequisite" should "be false if not satisfied" in {
    ItemPrerequisite(KeyItem("item", "not in inventory"))(storyModel) shouldEqual false
  }

  "A StatPrerequisite" should "be true if satisfied" in {
    statPrerequisite(storyModel) shouldEqual true
  }

  "A StatPrerequisite" should "be false if not satisfied" in {
    StatPrerequisite(StatName.Intelligence, 2)(storyModel) shouldEqual false
  }

  it should behave like serializationTest(itemPrerequisite)

  it should behave like serializationTest(statPrerequisite)

}
