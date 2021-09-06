package model.characters

import mock.MockFactory
import model.characters.properties.stats.{Stat, StatName}
import model.items.{AbstractItem, ConsumableItem, EquipItem, EquipItemType, KeyItem}
import specs.{FlatTestSpec, SerializableSpec}

class CharacterTest extends FlatTestSpec with SerializableSpec {

  val maxPS: Int = 100
  val wrongMaxPS: Int = -3
  val correctSetOfStats: Set[Stat] = MockFactory.mockSetOfStats()
  val mainPlayer: Player = Player("Jonathan", maxPS, correctSetOfStats)
  val easyEnemy: Enemy = Enemy("Yoshikage Kira", maxPS, correctSetOfStats)

  "The player" should "have name Jonathan" in {
    mainPlayer.name shouldEqual "Jonathan"
  }

  it should "throw IllegalArgumentException if the name is left empty" in {
    intercept[IllegalArgumentException] {
      Player("", maxPS, correctSetOfStats)
    }
  }

  it should "throw IllegalArgumentException if maxPS is zero" in {
    intercept[IllegalArgumentException] {
      Player("Jolyne", 0, correctSetOfStats)
    }
  }

  it should "throw IllegalArgumentException if maxPS is negative" in {
    intercept[IllegalArgumentException] {
      Player("Jolyne", wrongMaxPS, correctSetOfStats)
    }
  }

  it should "throw IllegalArgumentException if stats are empty" in {
    intercept[IllegalArgumentException] {
      Player("Joseph", maxPS, Set())
    }
  }

  it should "throw IllegalArgumentException if stats are not enough" in {
    intercept[IllegalArgumentException] {
      Player("Joseph", maxPS, Set(Stat(1, StatName.Intelligence)))
    }
  }

  it should "throw IllegalArgumentException if multiple stats with same name are passed" in {
    intercept[IllegalArgumentException] {
      Player("Joseph", maxPS, (for (n <- 0 to 5) yield Stat(n, StatName.Charisma)).toSet)
    }
  }

  it should "throw IllegalArgumentException if the correct number of stats is passed but now all 6 are present" in {
    intercept[IllegalArgumentException] {
      Player("Joseph", maxPS, Set(
        Stat(1, StatName.Strength),
        Stat(1, StatName.Constitution),
        Stat(1, StatName.Wisdom),
        Stat(1, StatName.Charisma),
        Stat(1, StatName.Intelligence),
        Stat(2, StatName.Intelligence)
      ))
    }
  }

  val keyItem: KeyItem = KeyItem("key", "it's a key")

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

  val equipItem: EquipItem = EquipItem("sword", "it's a sword", Set(), EquipItemType.Socks)
  val consumableItem: ConsumableItem = ConsumableItem("potion",
    "it's a potion",
    c => c.properties.health.currentPS += 10)
  val consumableItemSuper: ConsumableItem = ConsumableItem("super potion",
    "it's a super potion",
    c => c.properties.health.currentPS += 50)


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

  "The enemy" should "have name Yoshikage Kira" in {
    easyEnemy.name shouldEqual "Yoshikage Kira"
  }

  it should "throw IllegalArgumentException if the name is left empty" in {
    intercept[IllegalArgumentException] {
      Enemy("", maxPS, correctSetOfStats)
    }
  }

  it should behave like serializationTest(mainPlayer)

  it should behave like serializationTest(easyEnemy)

}
