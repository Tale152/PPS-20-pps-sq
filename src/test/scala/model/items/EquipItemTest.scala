package model.items

import mock.MockFactory.ItemFactory
import mock.MockFactory.ItemFactory._
import org.scalatest.BeforeAndAfterEach
import specs.{FlatTestSpec, ItemSpec, SerializableSpec}

class EquipItemTest extends FlatTestSpec with SerializableSpec with BeforeAndAfterEach with ItemSpec {

  override def beforeEach(): Unit = {
    super.beforeEach()
    player.inventory = List()
    player.equippedItems = Set()
  }

  val firstArmorEquipItem: EquipItem = ItemFactory.mockEquipItem(EquipItemType.Armor)

  "An Equip Item" should "have a name" in {
    firstArmorEquipItem.name shouldEqual itemName
  }

  it should "have a description" in {
    firstArmorEquipItem.description shouldEqual itemDescription
  }

  it should "not have an undefined name" in {
    intercept[IllegalArgumentException] {
      EquipItem(
        undefinedItemName,
        itemDescription,
        List(),
        EquipItemType.Armor
      )
    }
  }

  it should "not have an empty name" in {
    intercept[IllegalArgumentException] {
      EquipItem(
        "",
        itemDescription,
        List(),
        EquipItemType.Gloves
      )
    }
  }

  it should "not have an undefined description" in {
    intercept[IllegalArgumentException] {
      EquipItem(
        itemName,
        undefinedItemDescription,
        List(),
        EquipItemType.Boots
      )
    }
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      EquipItem(
        itemName,
        "",
        List(),
        EquipItemType.Helmet
      )
    }
  }

  it should "have a list of stat modifier" in {
    firstArmorEquipItem.statModifiers shouldEqual List()
  }

  it should "have a type" in {
    firstArmorEquipItem.equipItemType shouldEqual EquipItemType.Armor
  }

  it should "not have an undefined type" in {
    intercept[IllegalArgumentException] {
      EquipItem(
        itemName,
        itemDescription,
        List(),
        undefinedEquipItemType
      )
    }
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

  val glovesEquipItem: EquipItem = ItemFactory.mockEquipItem(EquipItemType.Gloves)
  val secondArmorEquipItem: EquipItem = ItemFactory.mockEquipItem(EquipItemType.Armor)

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

}
