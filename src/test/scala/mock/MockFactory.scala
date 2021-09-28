package mock

import model.StoryModel
import model.characters.{Enemy, Player}
import model.characters.properties.stats.{Stat, StatModifier, StatName}
import model.characters.properties.stats.StatName.StatName
import model.items.{ConsumableItem, EquipItem, EquipItemType, Item, KeyItem}
import model.nodes.{Pathway, StoryNode}

object MockFactory {

  object StatFactory {
    val defaultValue: Int = 10
    var undefinedStatName: StatName = _

    def strengthStat(value: Int = defaultValue): Stat = Stat(value, StatName.Strength)
    def wisdomStat(value: Int = defaultValue): Stat = Stat(value, StatName.Wisdom)
    def dexterityStat(value: Int = defaultValue): Stat = Stat(value, StatName.Dexterity)
  }

  object StatModifierFactory {
    var undefinedModifierStrategy: Int => Int = _
    val modifierStrategy: Int => Int = value => value * 2

    def statModifier(statName: StatName): StatModifier = StatModifier(statName, modifierStrategy)

    def statModifiers(statNames: StatName*): Set[StatModifier] = {
      var modifiers: Set[StatModifier] = Set()
      for (stat <- statNames){
        modifiers += statModifier(stat)
      }
      modifiers
    }
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

    def mockPlayer(maxPS: Int = maxPs): Player = Player("player", maxPS, mockSetOfStats())

    def mockEnemy(maxPs: Int = maxPs): Enemy = Enemy("enemy", maxPs, mockSetOfStats())
  }

  object ItemFactory {
    var undefinedItemName: String = _
    var undefinedItemDescription: String = _

    val mockKeyItem: KeyItem = KeyItem("key", "it's a key")
    val mockEquipItem: EquipItem = EquipItem("sword", "it's a sword", Set(), EquipItemType.Socks)
    val mockConsumableItem: ConsumableItem = ConsumableItem("potion",
      "it's a potion",
      c => c.properties.health.currentPS += 10)
    val mockSuperConsumableItem: ConsumableItem = ConsumableItem("super potion",
      "it's a super potion",
      c => c.properties.health.currentPS += 50)

    def insertItemInInventory(item: Item){
      player.inventory = item :: player.inventory
      player.inventory should contain (item)
    }

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
