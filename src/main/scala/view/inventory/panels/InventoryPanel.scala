package view.inventory.panels

import model.items.Item
import view.util.scalaQuestSwingComponents.{SqSwingGridPanel, SqSwingLabel}

import javax.swing.SwingConstants

/**
 * Panel that renders the list of all the items possessed by the player.
 *
 * @param inventoryItems the list containing a player's items.
 */
case class InventoryPanel(inventoryItems: List[Item]) extends SqSwingGridPanel(0, 1) {
  inventoryItems.foreach(c => this.add(SqSwingLabel("- " + c.name, alignment = SwingConstants.CENTER)))
}
