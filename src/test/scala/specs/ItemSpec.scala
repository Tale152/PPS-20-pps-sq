package specs

import mock.MockFactory.CharacterFactory
import model.items.Item
import model.characters.{Character, Enemy, Player}

trait ItemSpec { this: FlatTestSpec =>

  val maxPs = 100
  val player : Character = Player("JoJo", maxPs , CharacterFactory.mockSetOfStats())
  val enemy : Character = Enemy("Jonathan", maxPs , CharacterFactory.mockSetOfStats())

  var undefinedItemName: String = _
  val emptyItemName: String = ""
  var undefinedItemDescription: String = _
  val emptyItemDescription: String = ""

  def insertItemInInventory(item: Item){
    player.inventory = item :: player.inventory
    player.inventory should contain (item)
  }

}