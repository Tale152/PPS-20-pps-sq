package controller.game.subcontroller

import controller.game.GameMasterController
import model.StoryModel
import model.items.Item
import view.inventory.InventoryView

/**
 * Controller used to do operations on a [[model.characters.Character]] inventory.
 * Coupled with a [[view.history.HistoryView]]
 */
sealed trait InventoryController extends SubController {

  /**
   * Use the selected item.
   * @param item the item to use.
   */
  def use(item: Item): Unit

  /**
   * Discard the selected item. Item will be lost forever.
   * @param item the item to discard.
   */
  def discard(item: Item): Unit

}

object InventoryController {
  private class InventoryControllerImpl(private val gameMasterController: GameMasterController,
                                        private val storyModel: StoryModel) extends InventoryController {

    private val inventoryView: InventoryView = InventoryView(this)

    /**
     * Use the selected item.
     *
     * @param item the item to use.
     */
    override def use(item: Item): Unit = ???

    /**
     * Discard the selected item. Item will be lost forever.
     *
     * @param item the item to discard.
     */
    override def discard(item: Item): Unit = ???

    /**
     * Start the Controller.
     */
    override def execute(): Unit = ???

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = ???
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): InventoryController =
    new InventoryControllerImpl(gameMasterController, storyModel)
}
