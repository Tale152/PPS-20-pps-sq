package model.characters.items

import mock.MockFactory.ItemFactory
import mock.MockFactory.ItemFactory._
import model.items.ConsumableItem
import org.scalatest.BeforeAndAfterEach
import specs.{FlatTestSpec, ItemSpec, SerializableSpec}

class ConsumableItemTest extends FlatTestSpec with SerializableSpec with BeforeAndAfterEach with ItemSpec {

  override def beforeEach(): Unit = {
    super.beforeEach()
    player.inventory = List()
    player.equippedItems = Set()
  }

  val consumableItem : ConsumableItem = ItemFactory.mockConsumableItem

  "A Consumable Item" should "have a name" in {
    consumableItem.name shouldEqual itemName
  }

  it should "have a description" in {
    consumableItem.description shouldEqual itemDescription
  }

  it should "not have a undefined name" in {
    intercept[IllegalArgumentException] {
      ConsumableItem(
        undefinedItemName,
        itemDescription,
        consumableStrategy
      )
    }
  }

  it should "not have an empty name" in {
    intercept[IllegalArgumentException] {
      ConsumableItem(
        "",
        itemDescription,
        consumableStrategy
      )
    }
  }

  it should "not have a undefined description" in {
    intercept[IllegalArgumentException] {
      ConsumableItem(
        itemName,
        undefinedItemDescription,
        consumableStrategy
      )
    }
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      ConsumableItem(
        itemName,
        "",
        consumableStrategy
      )
    }
  }

  it should "work on the target" in {
    player.properties.health.currentPS -= 50 // take damage => 100 - 50 = 50
    consumableItem.use(player)() // restore ps => 50 + 10 = 60
    player.properties.health.currentPS shouldEqual 60
  }

  it should "disappear from the inventory after use" in {
    insertItemInInventory(consumableItem)
    consumableItem.use(player)()
    player.inventory should not contain consumableItem
  }

  it should "work on the target and be removed from the owner inventory after use" in {
    insertItemInInventory(consumableItem)
    enemy.properties.health.currentPS -= 50 // take damage => 100 - 50 = 50
    consumableItem.use(player)(enemy)
    enemy.properties.health.currentPS shouldEqual 60 // restore ps => 50 + 10 = 60
    player.inventory should not contain consumableItem
  }

  it should "be removed from inventory preserving other items" in {
    val secondConsumableItem : ConsumableItem = ConsumableItem(
      itemName,
      itemDescription,
      superConsumableStrategy
    )
    insertItemInInventory(consumableItem)
    insertItemInInventory(secondConsumableItem)
    player.inventory should have size 2

    consumableItem.use(player)()

    player.inventory should have size 1
    player.inventory should contain (secondConsumableItem)
  }

  it should behave like serializationTest(consumableItem)

}
