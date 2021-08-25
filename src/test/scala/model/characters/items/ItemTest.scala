package model.characters.items

import model.characters.Player
import model.characters.properties.stats.{Stat, StatName}
import model.items.{ConsumableItem, EquipItem, KeyItem}
import specs.{FlatTestSpec, SerializableSpec}

class ItemTest extends FlatTestSpec with SerializableSpec {

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

  val equipItemName : String = "Sword of Iron"
  val equipItemDescription : String = "Can cut things"
  val equipItem : EquipItem = EquipItem(equipItemName, equipItemDescription)

  "An Equip Item" should "have a name" in {
    equipItem.name shouldEqual equipItemName
  }

  it should "have a description" in {
    equipItem.description shouldEqual equipItemDescription
  }

  it should behave like serializationTest(equipItem)

  // Consumable Item Tests

  val consumableItemName : String = "Potion"
  val consumableItemDescription : String = "Restore 10 PS"
  val consumableItem : ConsumableItem = ConsumableItem(
    consumableItemName,
    consumableItemDescription,
    c => c.properties.health.currentPS += 10
  )

  val targetMaxPs = 100
  val targetCharacter : Player = Player("JoJo", targetMaxPs , Set(Stat(1, StatName.Speed)))


  "A Consumable Item" should "have a name" in {
    consumableItem.name shouldEqual consumableItemName
  }

  it should "have a description" in {
    consumableItem.description shouldEqual consumableItemDescription
  }

  it should "work on the target" in {
    targetCharacter.properties.health.currentPS -= 50 // take damage => 100 - 50 == 50
    consumableItem.consumableStrategy(targetCharacter) // restore ps => 50 + 10 = 60
    targetCharacter.properties.health.currentPS shouldEqual 60
  }

  /*
  it should "disappear from the inventory after use" in {
    //TODO add to targetCharacter inventory
    consumableItem.consumableStrategy(targetCharacter)
    //TODO test item has disappeared
  }
  */

  it should behave like serializationTest(consumableItem)

}
