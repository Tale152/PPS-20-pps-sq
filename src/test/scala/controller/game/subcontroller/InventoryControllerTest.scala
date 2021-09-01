package controller.game.subcontroller

import controller.game.GameMasterController
import mock.MockFactory.mockStoryModel
import model.StoryModel
import model.characters.properties.stats.{StatModifier, StatName}
import model.items.{ConsumableItem, EquipItem, EquipItemType, Item}
import org.scalatest.BeforeAndAfterEach
import specs.FlatTestSpec

class InventoryControllerTest extends FlatTestSpec with BeforeAndAfterEach {

  val playerMaxPS : Int = 100
  val storyModel: StoryModel = mockStoryModel(playerMaxPS)
  val gameMasterController: GameMasterController =  GameMasterController(storyModel)
  val inventoryController: InventoryController = InventoryController(gameMasterController, storyModel)

  val consumableItem: Item = ConsumableItem(
    "potion",
    "restore 10 ps",
    c => c.properties.health.currentPS += 10
  )
  val equipItem: Item = EquipItem(
    "socks",
    "gives +1 Speed",
    Set(StatModifier(StatName.Speed, s => s + 1)),
    EquipItemType.Socks
  )
  val anotherEquipItem: Item = EquipItem(
    "socksButBetter",
    "gives +5 Speed",
    Set(StatModifier(StatName.Speed, s => s + 5)),
    EquipItemType.Socks
  )
  storyModel.player.inventory = List(consumableItem, equipItem, anotherEquipItem)
  storyModel.player.properties.health.currentPS -= 50

  "The Inventory Controller" should "be able to use an item" in {
    inventoryController.use(storyModel.player.inventory.head)(storyModel.player) //use potion
    storyModel.player.inventory.size shouldEqual 2
    storyModel.player.properties.health.currentPS shouldEqual 60
    inventoryController.use(storyModel.player.inventory.head)(storyModel.player) //use socks
    storyModel.player.equippedItems.size shouldEqual 1
    storyModel.player.inventory.size shouldEqual 2
  }

  it should "be able to discard items" in {
    inventoryController.discard(storyModel.player.inventory.head)
    storyModel.player.inventory.size shouldEqual 1
    storyModel.player.equippedItems.size shouldEqual 0
    inventoryController.discard(storyModel.player.inventory.head)
    storyModel.player.inventory.size shouldEqual 0
  }

}
