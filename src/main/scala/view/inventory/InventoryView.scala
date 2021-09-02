package view.inventory

import controller.game.subcontroller.InventoryController
import model.items.Item
import view.AbstractView
import view.inventory.panels.inventoryPanel
import view.util.common.{ControlsPanel, Scrollable}
import view.util.scalaQuestSwingComponents.SqSwingLabel

import java.awt.BorderLayout
import javax.swing.{BorderFactory, SwingConstants}

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

  private val TitleSize = 25

  private class InventoryViewImpl(inventoryController: InventoryController) extends InventoryView {

    private var _inventoryItems: List[Item] = List()

    override def setItems(items: List[Item]): Unit = _inventoryItems = items

    this.setLayout(new BorderLayout())

    override def populateView(): Unit = {
      this.add(SqSwingLabel("Inventory", labelSize = TitleSize, alignment = SwingConstants.CENTER), BorderLayout.NORTH)
      val recap = Scrollable(inventoryPanel.InventoryPanel(inventoryController, _inventoryItems))
      recap.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0))
      this.add(recap, BorderLayout.CENTER)
      this.add(ControlsPanel(List(("b", ("[B] Back", _ => inventoryController.close())))), BorderLayout.SOUTH)
    }
  }

  def apply(inventoryController: InventoryController): InventoryView = new InventoryViewImpl(inventoryController)

}
