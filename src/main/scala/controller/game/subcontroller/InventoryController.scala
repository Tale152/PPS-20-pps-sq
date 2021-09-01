package controller.game.subcontroller

import model.items.Item

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

}
