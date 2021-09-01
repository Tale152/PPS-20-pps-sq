package view.inventory

import model.items.Item
import view.AbstractView

/**
 * A GUI that allows the user to view, use and discard the items in his possession.
 */
trait InventoryView extends AbstractView {

  /**
   * Show the items on the View.
   * @param items the items to display.
   */
  def showItems(items: List[Item]): Unit

}

object InventoryView {

}


