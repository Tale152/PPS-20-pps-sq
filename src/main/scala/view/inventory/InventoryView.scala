package view.inventory

import controller.game.subcontroller.InventoryController
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

  private class InventoryViewImpl(inventoryController: InventoryController) extends InventoryView {
    /**
     * Show the items on the View.
     *
     * @param items the items to display.
     */
    override def showItems(items: List[Item]): Unit = ???

    /**
     * Sub-portion of render() where graphical elements are added
     */
    override def populateView(): Unit = ???
  }

  def apply(inventoryController: InventoryController): InventoryView = new InventoryViewImpl(inventoryController)

}
