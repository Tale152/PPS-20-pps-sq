package model.characters.items

import model.items.KeyItem
import org.scalatest.BeforeAndAfterEach
import specs.{FlatTestSpec, ItemSpec, SerializableSpec}

class KeyItemsTest extends FlatTestSpec with SerializableSpec with BeforeAndAfterEach with ItemSpec {

  override def beforeEach(): Unit = {
    super.beforeEach()
    player.inventory = List()
    player.equippedItems = Set()
  }

  val keyItemName : String = "Key to Success"
  val keyItemDescription : String = "Leads you to success"
  val keyItem : KeyItem = KeyItem(keyItemName, keyItemDescription)

  "A key Item" should "have a name" in {
    keyItem.name shouldEqual keyItemName
  }

  it should "not have an undefined name" in {
    intercept[IllegalArgumentException] {
      KeyItem(undefinedItemName, keyItemDescription)
    }
  }

  it should "not have an empty name" in {
    intercept[IllegalArgumentException] {
      KeyItem(emptyItemName, keyItemDescription)
    }
  }

  it should "have a description" in {
    keyItem.description shouldEqual keyItemDescription
  }

  it should "not have an undefined description" in {
    intercept[IllegalArgumentException] {
      KeyItem(keyItemName, undefinedItemDescription)
    }
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      KeyItem(keyItemName, emptyItemDescription)
    }
  }

  it should "throw UsupporteOperationException when used" in {
    insertItemInInventory(keyItem)
    intercept[UnsupportedOperationException] {
      keyItem.use(player)()
    }
  }

  it should behave like serializationTest(keyItem)
}
