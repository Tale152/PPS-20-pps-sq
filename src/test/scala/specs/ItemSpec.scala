package specs

import model.items.Item
import model.characters.{Character, Enemy, Player}
import model.characters.properties.stats.{Stat, StatName}

trait ItemSpec { this: FlatTestSpec =>

  val maxPs = 100
  val player : Character = Player("JoJo", maxPs , Set(Stat(1, StatName.Speed)))
  val enemy : Character = Enemy("Jonathan", maxPs , Set(Stat(1, StatName.Speed)))

  var undefinedItemName: String = _
  val emptyItemName: String = ""
  var undefinedItemDescription: String = _
  val emptyItemDescription: String = ""

  def insertItemInInventory(item: Item){
    player.inventory = item :: player.inventory
    player.inventory should contain (item)
  }

}