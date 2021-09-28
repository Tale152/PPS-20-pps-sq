package specs

import mock.MockFactory.CharacterFactory
import model.items.Item
import model.characters.Character

trait ItemSpec { this: FlatTestSpec =>

  val player : Character = CharacterFactory.mockPlayer()
  val enemy : Character = CharacterFactory.mockEnemy()

  def insertItemInInventory(item: Item){
    player.inventory = item :: player.inventory
    player.inventory should contain (item)
  }

}
