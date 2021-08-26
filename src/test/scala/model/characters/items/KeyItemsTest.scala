package model.characters.items

import model.items.KeyItem
import org.scalatest.BeforeAndAfterEach
import specs.{FlatTestSpec, ItemSpec, SerializableSpec}

class KeyItemsTest extends FlatTestSpec with SerializableSpec with BeforeAndAfterEach with ItemSpec{

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

  it should "have a description" in {
    keyItem.description shouldEqual keyItemDescription
  }

  it should "remain in inventory when used" in {
    insertItemInInventory(keyItem)
    keyItem.use(player)()
    player.inventory should contain (keyItem)
  }

  it should behave like serializationTest(keyItem)
}
