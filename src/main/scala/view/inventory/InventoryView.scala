package view.inventory

import java.awt.BorderLayout

import controller.game.subcontroller.InventoryController
import javax.swing.SwingConstants
import model.items.Item
import view.AbstractView
import view.inventory.panels.inventoryPanel
import view.util.common.ControlsPanel
import view.util.scalaQuestSwingComponents.SqSwingLabel

/**
 * A GUI that allows the user to view, use and discard the items in his possession.
 */
sealed trait InventoryView extends AbstractView {

  /**
   * Shows the items on the View.
   *
   * @param items the items to display.
   */
  def setItems(items: List[Item]): Unit

}

object InventoryView {

  private val TitleSize = 25

  def apply(inventoryController: InventoryController): InventoryView = new InventoryViewImpl(inventoryController)

  private class InventoryViewImpl(inventoryController: InventoryController) extends InventoryView {

    private var _inventoryItems: List[Item] = List()

    override def setItems(items: List[Item]): Unit = _inventoryItems = items

    this.setLayout(new BorderLayout())

    override def populateView(): Unit = {
      this.add(SqSwingLabel("Inventory", labelSize = TitleSize, alignment = SwingConstants.CENTER), BorderLayout.NORTH)
      this.add(inventoryPanel.InventoryPanel(inventoryController, _inventoryItems), BorderLayout.CENTER)
      this.add(ControlsPanel(List(("b", ("[B] Back", _ => inventoryController.close())))), BorderLayout.SOUTH)
    }

  }

}
