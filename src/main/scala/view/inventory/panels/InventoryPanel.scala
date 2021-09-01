package view.inventory.panels

import controller.game.subcontroller.InventoryController
import model.items.{EquipItem, Item, KeyItem}
import model.characters.Character
import view.util.scalaQuestSwingComponents.SqSwingButton.SqSwingButton
import view.util.scalaQuestSwingComponents.SqSwingDialog.SqSwingDialog
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingGridPanel}

import java.awt.event.ActionEvent

case class InventoryPanel(inventoryController: InventoryController, inventoryItems: List[Item])
  extends SqSwingGridPanel(0, 1) {

  private object DialogPanelButtons {
    val useButton: Item => SqSwingButton = (item: Item) =>
      new SqSwingButton(
        "Use",
        (_: ActionEvent) => {
          val targets: Set[Character]  = inventoryController.targets()
          if(inventoryController.targets().size == 1) {
            inventoryController.use(item)(targets.head)
          } else {
            //TODO used when battle is occurring
          }
        },
        item match {
          case _: KeyItem => false
          case _ => true
        })

    val discardButton: Item => SqSwingButton = (item: Item) =>
      new SqSwingButton(
        "Discard",
        (_: ActionEvent) => inventoryController.discard(item),
        true
      )

    val goBackButton: SqSwingButton =
      new SqSwingButton(
        "Go Back",
        (_: ActionEvent) => {},
        true
      )
  }

  import DialogPanelButtons._

  private def itemDialog(item: Item): Unit = {
    SqSwingDialog(
      "Item Screen", "<html><h1>" + item.name + "</h1>" + item.description + "</html>",
      if (inventoryController.targets().size == 1) {
        List(useButton(item), discardButton(item), goBackButton)
      } else {
        List(useButton(item), goBackButton)
      }
    )
  }

  private def formattedItemName(item: Item) =
    item.name + " [" + item.getClass.getSimpleName + {
      item match {
        case equipItem : EquipItem if inventoryController.isEquipped(equipItem) =>
          "(" + equipItem.equipItemType.toString + ") - Equipped"
        case equipItem : EquipItem => "(" + equipItem.equipItemType.toString + ")"
        case _ => ""
      }
    } + "]"

  inventoryItems.foreach(item => this.add(
    SqSwingButton(
      formattedItemName(item),
      _ => itemDialog(item))
  ))
}
