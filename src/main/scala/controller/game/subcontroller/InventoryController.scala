package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel
import model.characters.Character
import model.items.{EquipItem, Item}
import view.inventory.InventoryView

/**
 * Controller used to do operations on a [[model.characters.Character]] inventory.
 * Coupled with a [[view.history.HistoryView]]
 */
sealed trait InventoryController extends SubController {

  /**
   * Use the selected item.
   *
   * @param item   the item to use.
   * @param target the target player for the item use.
   */
  def use(item: Item)(target: Character): Unit

  /**
   * Discard the selected item. Item will be lost forever.
   *
   * @param item the item to discard.
   */
  def discard(item: Item): Unit

  /**
   * @return the list of all the possible targets. The main [[model.characters.Player]] is always included.
   */
  def targets(): Set[Character]

  /**
   * Check if a certain [[model.items.EquipItem]] is equipped.
   * @param equipItem the [[model.items.EquipItem]] that might be equipped.
   * @return true if the item is equipped, false otherwise.
   */
  def isEquipped(equipItem: EquipItem): Boolean

}

object InventoryController {
  private class InventoryControllerImpl(private val gameMasterController: GameMasterController,
                                        private val storyModel: StoryModel) extends InventoryController {

    private val inventoryView: InventoryView = InventoryView(this)

    private def updateView(): Unit = {
      if(isPlayerDead){
        inventoryView.committedSuicide()
      } else {
        inventoryView.setItems(storyModel.player.inventory)
        inventoryView.render()
      }
    }

    /**
     * @return true of the player health is now 0 after the usage of an item.
     */
    private def isPlayerDead: Boolean = storyModel.player.properties.health.currentPS == 0

    override def use(item: Item)(target: Character): Unit = {
      item.use(storyModel.player)(target)
      updateView()
    }

    override def discard(item: Item): Unit = {
      item match {
        case equipItem: EquipItem if storyModel.player.equippedItems.contains(equipItem) =>
          storyModel.player.equippedItems = storyModel.player.equippedItems.filter(_ != equipItem)
        case _ =>
      }
      storyModel.player.inventory = storyModel.player.inventory.filter(_ != item)
      updateView()
    }

    override def execute(): Unit = {
      inventoryView.setItems(storyModel.player.inventory)
      inventoryView.render()
    }

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit =
      if(isPlayerDead) {
        gameMasterController.close()
      } else if(currentlyInBattle()) {
        gameMasterController.executeOperation(OperationType.BattleOperation)
      } else {
        gameMasterController.executeOperation(OperationType.StoryOperation)
      }

    private def currentlyInBattle(): Boolean =
      storyModel.currentStoryNode.enemy.isDefined &&
        storyModel.currentStoryNode.enemy.get.properties.health.currentPS > 0

    /**
     * @return the list of all the possible targets.
     */
    override def targets(): Set[Character] =
      if(currentlyInBattle()){
        Set(storyModel.player, storyModel.currentStoryNode.enemy.get)
      } else {
        Set(storyModel.player)
      }

    override def isEquipped(equipItem: EquipItem): Boolean = storyModel.player.equippedItems.contains(equipItem)
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): InventoryController =
    new InventoryControllerImpl(gameMasterController, storyModel)
}
