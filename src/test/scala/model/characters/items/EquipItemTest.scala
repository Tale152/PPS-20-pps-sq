package model.characters.items

import model.items.EquipItemType.EquipItemType
import model.items.{EquipItem, EquipItemType}
import org.scalatest.BeforeAndAfterEach
import specs.{FlatTestSpec, ItemSpec, SerializableSpec}

class EquipItemTest extends FlatTestSpec with SerializableSpec with BeforeAndAfterEach with ItemSpec {

  override def beforeEach(): Unit = {
    super.beforeEach()
    player.inventory = List()
    player.equippedItems = Set()
  }

  val equipItemName: String = "Sword of Iron"
  val equipItemDescription: String = "Can cut things"
  val equipItemType: EquipItemType = EquipItemType.Armor
  var undefinedEquipItemType: EquipItemType = _

  val firstArmorEquipItem: EquipItem = EquipItem(equipItemName, equipItemDescription, Set(), equipItemType)

  "An Equip Item" should "have a name" in {
    firstArmorEquipItem.name shouldEqual equipItemName
  }

  it should "not have an undefined name" in {
    intercept[IllegalArgumentException] {
      EquipItem(undefinedItemName, equipItemDescription, Set(), equipItemType)
    }
  }

  it should "not have an empty name" in {
    intercept[IllegalArgumentException] {
      EquipItem(emptyItemName, equipItemDescription, Set(), equipItemType)
    }
  }

  it should "have a description" in {
    firstArmorEquipItem.description shouldEqual equipItemDescription
  }

  it should "not have an undefined description" in {
    intercept[IllegalArgumentException] {
      EquipItem(equipItemName, undefinedItemDescription, Set(), equipItemType)
    }
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      EquipItem(equipItemName, emptyItemDescription, Set(), equipItemType)
    }
  }

  it should "have a set of stat modifier" in {
    firstArmorEquipItem.statModifiers shouldEqual Set()
  }

  it should "have a type" in {
    firstArmorEquipItem.equipItemType shouldEqual equipItemType
  }

  it should "not have an undefined type" in {
    intercept[IllegalArgumentException] {
      EquipItem(equipItemName, equipItemDescription, Set(), undefinedEquipItemType)
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
}
