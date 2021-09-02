package view.inventory.panels.inventoryPanel

import controller.game.subcontroller.InventoryController
import model.items.{EquipItem, Item}
import view.inventory.panels.inventoryPanel.InventoryPanelButtons.{discardButton, goBackButton, useButton}
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingGridPanel}


case class InventoryPanel(inventoryController: InventoryController, inventoryItems: List[Item])
  extends SqSwingGridPanel(0, 1) {

  private def itemDialog(item: Item): Unit = {
    SqSwingDialog(
      "Item Screen", "<html><h1>" + item.name + "</h1>" + item.description + "</html>",
      if (inventoryController.targets().size == 1) {
        List(useButton(inventoryController, item), discardButton(inventoryController, item), goBackButton())
      } else {
        List(useButton(inventoryController, item), goBackButton())
      }
    )
  }

  private def formattedItemName(item: Item) =
    item.name + " [" + item.getClass.getSimpleName + {
      item match {
        case equipItem: EquipItem if inventoryController.isEquipped(equipItem) =>
          "(" + equipItem.equipItemType.toString + ") - Equipped"
        case equipItem: EquipItem => "(" + equipItem.equipItemType.toString + ")"
        case _ => ""
      }
    } + "]"

  inventoryItems.foreach(item => this.add(
    SqSwingButton(
      formattedItemName(item),
      _ => itemDialog(item))
  ))
}
