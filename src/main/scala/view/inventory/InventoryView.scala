package view.inventory

import controller.game.subcontroller.InventoryController
import model.items.Item
import view.AbstractView
import view.util.scalaQuestSwingComponents.SqSwingCenteredLabel

import java.awt.BorderLayout

/**
 * A GUI that allows the user to view, use and discard the items in his possession.
 */
trait InventoryView extends AbstractView {

  /**
   * Show the items on the View.
   *
   * @param items the items to display.
   */
  def setItems(items: List[Item]): Unit

}

object InventoryView {

  private class InventoryViewImpl(inventoryController: InventoryController) extends InventoryView {
    /**
     * Show the items on the View.
     *
     * @param items the items to display.
     */
    override def setItems(items: List[Item]): Unit = {
      List()
    }

    override def populateView(): Unit = {
      this.setLayout(new BorderLayout())
      this.add(SqSwingCenteredLabel("PROVA"), BorderLayout.CENTER)
      println("CI SONO ARRIVATO ")
    }
  }

  def apply(inventoryController: InventoryController): InventoryView = new InventoryViewImpl(inventoryController)

}
