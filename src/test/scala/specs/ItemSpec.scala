package specs

import model.items.Item
import model.characters.{Character, Player}
import model.characters.properties.stats.{Stat, StatName}

trait ItemSpec { this: FlatTestSpec =>

  val maxPs = 100
  val player : Character = Player("JoJo", maxPs , Set(Stat(1, StatName.Speed)))

  def insertItemInInventory(item: Item){
    player.inventory = item :: player.inventory
    player.inventory should contain (item)
  }
}