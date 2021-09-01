package view.inventory.panels

import model.items.Item
import view.util.scalaQuestSwingComponents.{SqSwingCenteredLabel, SqSwingGridPanel}

case class InventoryPanel(inventoryItems: List[Item]) extends SqSwingGridPanel(0, 1) {
  inventoryItems.foreach(c => this.add(SqSwingCenteredLabel("- " + c.name)))
}
