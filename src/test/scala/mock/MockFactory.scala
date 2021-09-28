package mock

import model.StoryModel
import model.characters.{Enemy, Player}
import model.characters.properties.stats.Stat
import model.characters.properties.stats.StatName
import model.characters.properties.stats.StatName.StatName
import model.items.{ConsumableItem, EquipItem, EquipItemType, KeyItem}
import model.nodes.{Pathway, StoryNode}

object MockFactory {

  object StatFactory {
    val defaultStrengthValue: Int = 10

    var undefinedStatName: StatName = _
    def strengthStat(value: Int = defaultStrengthValue): Stat = Stat(value, StatName.Strength)
  }

  object CharacterFactory {

    val playerName: String = "player"
    val enemyName: String = "enemy"
    val negativeMaxPs: Int = -1
    val maxPs: Int = 100

    def mockSetOfStats(): Set[Stat] = {
      Set(
        Stat(1, StatName.Strength),
        Stat(1, StatName.Constitution),
        Stat(1, StatName.Wisdom),
        Stat(1, StatName.Charisma),
        Stat(1, StatName.Intelligence),
        Stat(1, StatName.Dexterity)
      )
    }

    def mockPlayer(maxPS: Int): Player = Player("player", maxPS, mockSetOfStats())

    def mockEnemy(maxPs: Int): Enemy = Enemy("enemy", maxPs, mockSetOfStats())
  }

  object ItemFactory {
    val mockKeyItem: KeyItem = KeyItem("key", "it's a key")
    val mockEquipItem: EquipItem = EquipItem("sword", "it's a sword", Set(), EquipItemType.Socks)
    val mockConsumableItem: ConsumableItem = ConsumableItem("potion",
      "it's a potion",
      c => c.properties.health.currentPS += 10)
    val mockSuperConsumableItem: ConsumableItem = ConsumableItem("super potion",
      "it's a super potion",
      c => c.properties.health.currentPS += 50)

  }

  import mock.MockFactory.CharacterFactory.mockPlayer

  def mockStoryNode(): StoryNode = {
    val destinationNode: StoryNode = StoryNode(1, "narrative", None, Set.empty, List())
    val pathway: Pathway = Pathway("description", destinationNode, None)
    StoryNode(0, "narrative", None, Set(pathway), List())
  }

  def mockStoryModel(playerMaxPS: Int): StoryModel = {
    StoryModel("s", mockPlayer(playerMaxPS), mockStoryNode())
  }

}
