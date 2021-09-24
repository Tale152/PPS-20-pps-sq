package view.inventory.panels.inventoryPanel

import controller.game.subcontroller.InventoryController
import controller.util.serialization.StringUtil.StringFormatUtil.formatted
import model.items.{EquipItem, Item}
import view.inventory.panels.inventoryPanel.InventoryPanelButtons.{discardButton, goBackButton, useButton}
import view.util.common.{Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingGridPanel}

/**
 * Panel used to shows all the items in the inventory, correctly formatted. Used in [[view.inventory.InventoryView]].
 * @param inventoryController the controller that is used to modify the actual inventory of the player; it can use
 *                            and remove items.
 * @param inventoryItems the current list of items in inventory.
 */
case class InventoryPanel(inventoryController: InventoryController, inventoryItems: List[Item])
  extends SqSwingGridPanel(0, 1) {

  private def itemDialog(item: Item): Unit = {
    SqSwingDialog(
      "Item Screen", formatted("<h1>" + item.name + "</h1>" + item.description),
      if (inventoryController.targets().size == 1) {
        List(useButton(inventoryController, item), discardButton(inventoryController, item), goBackButton())
      } else {
        List(useButton(inventoryController, item), goBackButton())
      }
    )
  }

  private def formattedItemName(item: Item) =
    item.name + " [" + item.getClass.getSimpleName.replace("Item", "") + {
      item match {
        case equipItem: EquipItem if inventoryController.isEquipped(equipItem) =>
          " (" + equipItem.equipItemType.toString + ") *"
        case equipItem: EquipItem => " (" + equipItem.equipItemType.toString + ")"
        case _ => ""
      }
    } + "]"

  this.add(Scrollable(VerticalButtons(inventoryItems.map(item =>
    SqSwingButton(formattedItemName(item), _ => itemDialog(item)))))
  )

}
