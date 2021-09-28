package model.characters

import mock.MockFactory.{CharacterFactory, ItemFactory}
import model.characters.properties.stats.Stat
import model.characters.properties.stats.StatName
import model.items.{AbstractItem, ConsumableItem, EquipItem, KeyItem}
import mock.MockFactory.CharacterFactory.{maxPs, negativeMaxPs, playerName, enemyName}
import specs.{FlatTestSpec, SerializableSpec}

class CharacterTest extends FlatTestSpec with SerializableSpec {

  val correctSetOfStats: Set[Stat] = CharacterFactory.mockSetOfStats()
  val mainPlayer: Player = CharacterFactory.mockPlayer(maxPs)
  val enemy: Enemy = CharacterFactory.mockEnemy(maxPs)

  "The player" should "have name player" in {
    mainPlayer.name shouldEqual playerName
  }

  it should "throw IllegalArgumentException if the name is left empty" in {
    intercept[IllegalArgumentException] {
      Player("", maxPs, correctSetOfStats)
    }
  }

  it should "throw IllegalArgumentException if maxPs is zero" in {
    intercept[IllegalArgumentException] {
      Player(playerName, 0, correctSetOfStats)
    }
  }

  it should "throw IllegalArgumentException if maxPS is negative" in {
    intercept[IllegalArgumentException] {
      Player(playerName, negativeMaxPs, correctSetOfStats)
    }
  }

  it should "throw IllegalArgumentException if stats are empty" in {
    intercept[IllegalArgumentException] {
      Player(playerName, maxPs, Set())
    }
  }

  it should "throw IllegalArgumentException if stats are not enough" in {
    intercept[IllegalArgumentException] {
      Player(playerName, maxPs, Set(Stat(1, StatName.Intelligence)))
    }
  }

  it should "throw IllegalArgumentException if multiple stats with same name are passed" in {
    intercept[IllegalArgumentException] {
      Player(playerName, maxPs, (for (n <- 0 to 5) yield Stat(n, StatName.Charisma)).toSet)
    }
  }

  it should "throw IllegalArgumentException if the correct number of stats is passed but now all 6 are present" in {
    intercept[IllegalArgumentException] {
      Player(playerName, maxPs, Set(
        Stat(1, StatName.Strength),
        Stat(1, StatName.Constitution),
        Stat(1, StatName.Wisdom),
        Stat(1, StatName.Charisma),
        Stat(1, StatName.Intelligence),
        Stat(2, StatName.Intelligence)
      ))
    }
  }

  val keyItem: KeyItem = ItemFactory.mockKeyItem

  it should "be able to add and remove items from inventory" in {
    mainPlayer.inventory = List(keyItem)
    mainPlayer.inventory contains keyItem shouldBe true
    mainPlayer.inventory.size shouldEqual 1
  }

  it should "be able to add multiple equal items to the inventory" in {
    mainPlayer.inventory = List(keyItem, keyItem)
    mainPlayer.inventory contains keyItem shouldBe true
    mainPlayer.inventory.size shouldEqual 2
  }

  val equipItem: EquipItem = ItemFactory.mockEquipItem
  val consumableItem: ConsumableItem = ItemFactory.mockConsumableItem
  val consumableItemSuper: ConsumableItem = ItemFactory.mockSuperConsumableItem

  it should "have a ordered inventory" in {
    mainPlayer.inventory = List(consumableItemSuper, consumableItem, keyItem, equipItem)
    mainPlayer.inventory.size shouldEqual 4
    mainPlayer.inventory shouldEqual List(consumableItem, consumableItemSuper, equipItem, keyItem)
  }

  it should "throw an IllegalArgumentException trying to order Items that are not of a specific class" in {
    val abstractItemOnTheFly = new AbstractItem("Conqueror Haki","All enemies will faint when used") {
      override def applyEffect(owner: Character)(target: Character): Unit = {}
      override def postEffect(owner: Character)(target: Character): Unit = {}
    }
    intercept[IllegalArgumentException] {
      mainPlayer.inventory = List(consumableItemSuper, consumableItem, keyItem, equipItem, abstractItemOnTheFly)
    }
  }

  "The enemy" should "have name enemy" in {
    enemy.name shouldEqual enemyName
  }

  it should "throw IllegalArgumentException if the name is left empty" in {
    intercept[IllegalArgumentException] {
      Enemy("", maxPs, correctSetOfStats)
    }
  }

  it should behave like serializationTest(mainPlayer)

  it should behave like serializationTest(enemy)

}
