package model.characters.items

import model.characters.Player
import model.characters.properties.stats.{Stat, StatName}
import model.items.EquipItemType.EquipItemType
import model.items.{ConsumableItem, EquipItem, EquipItemType, KeyItem}
import specs.{FlatTestSpec, SerializableSpec}

class ItemTest extends FlatTestSpec with SerializableSpec {

  val maxPs = 100
  val character : Player = Player("JoJo", maxPs , Set(Stat(1, StatName.Speed)))

  // Key Item tests

  val keyItemName : String = "Key to Success"
  val keyItemDescription : String = "Leads you to success"
  val keyItem : KeyItem = KeyItem(keyItemName, keyItemDescription)

  "A key Item" should "have a name" in {
    keyItem.name shouldEqual keyItemName
  }

  it should "have a description" in {
    keyItem.description shouldEqual keyItemDescription
  }

  it should behave like serializationTest(keyItem)

  // Equip Item Tests

  val equipItemName: String = "Sword of Iron"
  val equipItemDescription: String = "Can cut things"
  val equipItemType: EquipItemType = EquipItemType.Armor
  val equipItem: EquipItem = EquipItem(equipItemName, equipItemDescription, Set(), equipItemType)

  "An Equip Item" should "have a name" in {
    equipItem.name shouldEqual equipItemName
  }

  it should "have a description" in {
    equipItem.description shouldEqual equipItemDescription
  }

  it should "have a set of stat modifier" in {
    equipItem.statModifiers shouldEqual Set()
  }

  it should "have a type" in {
    equipItem.equipItemType shouldEqual equipItemType
  }

  it should "be equipped to the target" in {
    character.equippedItems should not contain equipItem
    equipItem.applyEffect(character)
    character.equippedItems should contain (equipItem)
  }

  it should "disappear from the equipped items after use" in {
    equipItem.postEffect(character)
    character.equippedItems should not contain equipItem
  }
  it should behave like serializationTest(equipItem)

  // Consumable Item Tests

  val consumableItemName : String = "Potion"
  val consumableItemDescription : String = "Restore 10 PS"
  val consumableItem : ConsumableItem = ConsumableItem(
    consumableItemName,
    consumableItemDescription,
    character,
    c => c.properties.health.currentPS += 10
  )

  "A Consumable Item" should "have a name" in {
    consumableItem.name shouldEqual consumableItemName
  }

  it should "have a description" in {
    consumableItem.description shouldEqual consumableItemDescription
  }

  it should "work on the target" in {
    character.properties.health.currentPS -= 50 // take damage => 100 - 50 == 50
    consumableItem.applyEffect(character) // restore ps => 50 + 10 = 60
    character.properties.health.currentPS shouldEqual 60
  }

  it should "disappear from the inventory after use" in {
    character.inventory += consumableItem
    consumableItem.postEffect(character)
    character.inventory should not contain consumableItem
  }

  it should behave like serializationTest(consumableItem)

}
