package model.characters.items

import model.items.EquipItemType.EquipItemType
import model.items.{ConsumableItem, EquipItem, EquipItemType}
import org.scalatest.BeforeAndAfterEach
import specs.{FlatTestSpec, ItemSpec, SerializableSpec}


class ItemTest extends FlatTestSpec with SerializableSpec with BeforeAndAfterEach with ItemSpec {

  override def beforeEach(): Unit = {
    super.beforeEach()
    player.inventory = List()
    player.equippedItems = Set()
  }

  // Equip Item Tests

  val equipItemName: String = "Sword of Iron"
  val equipItemDescription: String = "Can cut things"
  val equipItemType: EquipItemType = EquipItemType.Armor
  val firstArmorEquipItem: EquipItem = EquipItem(equipItemName, equipItemDescription, Set(), equipItemType)

  "An Equip Item" should "have a name" in {
    firstArmorEquipItem.name shouldEqual equipItemName
  }

  it should "have a description" in {
    firstArmorEquipItem.description shouldEqual equipItemDescription
  }

  it should "have a set of stat modifier" in {
    firstArmorEquipItem.statModifiers shouldEqual Set()
  }

  it should "have a type" in {
    firstArmorEquipItem.equipItemType shouldEqual equipItemType
  }

  it should "be equipped to the target" in {
    insertItemInInventory(firstArmorEquipItem)
    player.equippedItems should not contain firstArmorEquipItem
    firstArmorEquipItem.use(player)()
    player.equippedItems should contain (firstArmorEquipItem)
    player.inventory should contain (firstArmorEquipItem)
  }

  it should "disappear from the equipped items when used again" in {
    insertItemInInventory(firstArmorEquipItem)

    firstArmorEquipItem.use(player)()

    player.equippedItems should contain (firstArmorEquipItem)

    firstArmorEquipItem.use(player)()
    player.equippedItems should not contain firstArmorEquipItem
    player.inventory should contain (firstArmorEquipItem)
  }

  val glovesEquipItem: EquipItem = EquipItem(equipItemName, equipItemDescription, Set(), EquipItemType.Gloves)
  val secondArmorEquipItem: EquipItem = EquipItem(equipItemName, equipItemDescription, Set(), equipItemType)

  it should "be removed from equipped items when a new item with the same type is used" in {
    insertItemInInventory(firstArmorEquipItem)
    insertItemInInventory(secondArmorEquipItem)

    firstArmorEquipItem.use(player)()
    player.equippedItems should contain (firstArmorEquipItem)

    secondArmorEquipItem.use(player)()
    player.equippedItems.find(i => i.equipItemType == EquipItemType.Armor).get eq firstArmorEquipItem shouldBe false
    player.equippedItems.find(i => i.equipItemType == EquipItemType.Armor).get eq secondArmorEquipItem shouldBe true

    player.inventory should contain (firstArmorEquipItem)
    player.inventory should contain (secondArmorEquipItem)
  }

  "A new equip item" should "be equipped if it is a different type of equip item" in {
    insertItemInInventory(glovesEquipItem)
    insertItemInInventory(secondArmorEquipItem)

    secondArmorEquipItem.use(player)()
    glovesEquipItem.use(player)()

    player.equippedItems should contain (glovesEquipItem)
    player.equippedItems should contain (secondArmorEquipItem)
  }

  it should behave like serializationTest(firstArmorEquipItem)

  // Consumable Item Tests

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

  it should "have a description" in {
    consumableItem.description shouldEqual consumableItemDescription
  }

  it should "work on the target" in {
    player.properties.health.currentPS -= 50 // take damage => 100 - 50 == 50
    consumableItem.use(player)() // restore ps => 50 + 10 = 60
    player.properties.health.currentPS shouldEqual 60
  }

  it should "disappear from the inventory after use" in {
    insertItemInInventory(consumableItem)
    consumableItem.use(player)()
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
