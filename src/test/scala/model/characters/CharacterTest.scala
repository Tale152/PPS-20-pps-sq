package model.characters

import model.characters.properties.stats.{Stat, StatName}
import model.items.{AbstractItem, ConsumableItem, EquipItem, EquipItemType, KeyItem}
import specs.{FlatTestSpec, SerializableSpec}

class CharacterTest extends FlatTestSpec with SerializableSpec {

  val maxPS: Int = 100
  val wrongMaxPS: Int = -3
  val stats: Set[Stat] = Set(Stat(1, StatName.Speed))
  val mainPlayer: Player = Player("Jonathan", maxPS, stats)
  val easyEnemy: Enemy = Enemy("Yoshikage Kira", maxPS, stats)

  "The player" should "have name Jonathan" in {
    mainPlayer.name shouldEqual "Jonathan"
  }

  it should "throw IllegalArgumentException if the name is left empty" in {
    intercept[IllegalArgumentException] {
      Player("", maxPS, stats)
    }
  }

  it should "throw IllegalArgumentException if maxPS is zero" in {
    intercept[IllegalArgumentException] {
      Player("Jolyne", 0, stats)
    }
  }

  it should "throw IllegalArgumentException if maxPS is negative" in {
    intercept[IllegalArgumentException] {
      Player("Jolyne", wrongMaxPS, stats)
    }
  }

  it should "throw IllegalArgumentException if stats are empty" in {
    intercept[IllegalArgumentException] {
      Player("Joseph", maxPS, Set())
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
      Enemy("", maxPS, stats)
    }
  }

  it should behave like serializationTest(mainPlayer)

  it should behave like serializationTest(easyEnemy)

}
