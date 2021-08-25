package model.characters.items

import model.characters.Player
import model.characters.properties.stats.{Stat, StatName}
import model.items.EquipItemType.EquipItemType
import model.items.{ConsumableItem, EquipItem, EquipItemType, KeyItem}
import org.scalatest.BeforeAndAfterEach
import specs.{FlatTestSpec, SerializableSpec}


class ItemTest extends FlatTestSpec with SerializableSpec with BeforeAndAfterEach {

  val maxPs = 100
  val character : Player = Player("JoJo", maxPs , Set(Stat(1, StatName.Speed)))

  override def beforeEach(): Unit = {
    super.beforeEach()
    character.inventory = List()
    character.equippedItems = Set()
  }
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
    character.inventory = firstArmorEquipItem :: character.inventory
    character.inventory should contain (firstArmorEquipItem)
    character.equippedItems should not contain firstArmorEquipItem
    firstArmorEquipItem.use(character)()
    character.equippedItems should contain (firstArmorEquipItem)
    character.inventory should contain (firstArmorEquipItem)
  }

  it should "disappear from the equipped items when used again" in {
    character.inventory = firstArmorEquipItem :: character.inventory
    character.inventory should contain (firstArmorEquipItem)
    firstArmorEquipItem.use(character)()
    character.equippedItems should contain (firstArmorEquipItem)

    firstArmorEquipItem.use(character)()
    character.equippedItems should not contain firstArmorEquipItem
    character.inventory should contain (firstArmorEquipItem)
  }

  val glovesEquipItem: EquipItem = EquipItem(equipItemName, equipItemDescription, Set(), EquipItemType.Gloves)
  val secondArmorEquipItem: EquipItem = EquipItem(equipItemName, equipItemDescription, Set(), equipItemType)

  it should "be removed from equipped items when a new item with the same type is used" in {
    character.inventory = secondArmorEquipItem :: character.inventory
    character.inventory = firstArmorEquipItem :: character.inventory
    character.inventory should contain (firstArmorEquipItem)
    character.inventory should contain (secondArmorEquipItem)

    firstArmorEquipItem.use(character)()
    character.equippedItems should contain (firstArmorEquipItem)

    secondArmorEquipItem.use(character)()
    character.equippedItems.find(i => i.equipItemType == EquipItemType.Armor).get eq firstArmorEquipItem shouldBe false
    character.equippedItems.find(i => i.equipItemType == EquipItemType.Armor).get eq secondArmorEquipItem shouldBe true

    character.inventory should contain (firstArmorEquipItem)
    character.inventory should contain (secondArmorEquipItem)
  }

  "A new equip item" should "be equipped if it is a different type of equip item" in {
    character.inventory = glovesEquipItem :: character.inventory
    character.inventory = secondArmorEquipItem :: character.inventory

    character.inventory should contain (glovesEquipItem)
    secondArmorEquipItem.use(character)()
    glovesEquipItem.use(character)()

    character.equippedItems should contain (glovesEquipItem)
    character.equippedItems should contain (secondArmorEquipItem)
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
    character.properties.health.currentPS -= 50 // take damage => 100 - 50 == 50
    consumableItem.use(character)() // restore ps => 50 + 10 = 60
    character.properties.health.currentPS shouldEqual 60
  }

  it should "disappear from the inventory after use" in {
    character.inventory = consumableItem :: character.inventory
    consumableItem.use(character)()
    character.inventory should not contain consumableItem
  }

  it should "be removed from inventory preserving other items" in {
    val secondConsumableItem : ConsumableItem = ConsumableItem(
      consumableItemName,
      consumableItemDescription,
      c => c.properties.health.currentPS += 10
    )
    character.inventory = consumableItem :: character.inventory
    character.inventory = secondConsumableItem :: character.inventory
    character.inventory should have size 2
    consumableItem.use(character)()
    character.inventory should have size 1
    character.inventory should contain (secondConsumableItem)
  }

  it should behave like serializationTest(consumableItem)

}
