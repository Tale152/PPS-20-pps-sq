package model.characters.items

import model.items.ConsumableItem
import org.scalatest.BeforeAndAfterEach
import specs.{FlatTestSpec, ItemSpec, SerializableSpec}

class ConsumableItemTest extends FlatTestSpec with SerializableSpec with BeforeAndAfterEach with ItemSpec {

  override def beforeEach(): Unit = {
    super.beforeEach()
    player.inventory = List()
    player.equippedItems = Set()
  }

  val consumableItemName : String = "Potion"
  val consumableItemDescription : String = "Restore 10 PS"
  val consumableItem : ConsumableItem = ConsumableItem(
    consumableItemName,
    consumableItemDescription,
    c => c.properties.health.currentPS += 10
  )

  "A Consumable Item" should "have a name" in {
    consumableItem.name shouldEqual consumableItemName
  }

  it should "not have a undefined name" in {
    intercept[IllegalArgumentException] {
      ConsumableItem(
        undefinedItemName,
        consumableItemDescription,
        c => c.properties.health.currentPS += 10
      )
    }
  }

  it should "not have an empty name" in {
    intercept[IllegalArgumentException] {
      ConsumableItem(
        emptyItemName,
        consumableItemDescription,
        c => c.properties.health.currentPS += 10
      )
    }
  }

  it should "have a description" in {
    consumableItem.description shouldEqual consumableItemDescription
  }

  it should "not have a undefined description" in {
    intercept[IllegalArgumentException] {
      ConsumableItem(
        consumableItemName,
        undefinedItemDescription,
        c => c.properties.health.currentPS += 10
      )
    }
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      ConsumableItem(
        consumableItemName,
        emptyItemDescription,
        c => c.properties.health.currentPS += 10
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
      consumableItemName,
      consumableItemDescription,
      c => c.properties.health.currentPS += 10
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
