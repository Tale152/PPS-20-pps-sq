package model.items

import mock.MockFactory.ItemFactory
import mock.MockFactory.ItemFactory._
import model.characters.properties.PropertiesContainer
import model.items.KeyItem
import org.scalatest.BeforeAndAfterEach
import specs.{FlatTestSpec, ItemSpec, SerializableSpec}

class KeyItemsTest extends FlatTestSpec with SerializableSpec with BeforeAndAfterEach with ItemSpec {

  override def beforeEach(): Unit = {
    super.beforeEach()
    player.inventory = List()
    player.equippedItems = Set()
  }

  val keyItem : KeyItem = ItemFactory.mockKeyItem

  "A key Item" should "have a name" in {
    keyItem.name shouldEqual itemName
  }

  it should "not have an undefined name" in {
    intercept[IllegalArgumentException] {
      KeyItem(
        undefinedItemName,
        itemDescription
      )
    }
  }

  it should "not have an empty name" in {
    intercept[IllegalArgumentException] {
      KeyItem(
        "",
        itemDescription
      )
    }
  }

  it should "have a description" in {
    keyItem.description shouldEqual itemDescription
  }

  it should "not have an undefined description" in {
    intercept[IllegalArgumentException] {
      KeyItem(
        itemName,
        undefinedItemDescription
      )
    }
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      KeyItem(
        itemName,
        ""
      )
    }
  }

  it should "throw UnsupportedOperationException when used" in {
    insertItemInInventory(keyItem)
    intercept[UnsupportedOperationException] {
      keyItem.use(player)()
    }
  }

  it should "do nothing in post effect" in {
    val properties: PropertiesContainer = player.properties
    keyItem.postEffect(player)()
    player.properties shouldEqual properties
  }

  it should behave like serializationTest(keyItem)

}
