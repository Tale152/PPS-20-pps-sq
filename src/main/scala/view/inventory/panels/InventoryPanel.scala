package view.inventory.panels

import model.items.{EquipItem, Item, KeyItem}
import view.util.scalaQuestSwingComponents.SqSwingButton.SqSwingButton
import view.util.scalaQuestSwingComponents.SqSwingDialog.SqSwingDialog
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingGridPanel}

import java.awt.event.ActionEvent

case class InventoryPanel(inventoryItems: List[Item]) extends SqSwingGridPanel(0, 1) {

  private object DialogPanelButtons {
    val useButton: Item => SqSwingButton = (item: Item) =>
      new SqSwingButton(
        "Use",
        (_: ActionEvent) => {},
        item match {
          case _: KeyItem => false
          case _ => true
        })

    val discardButton: Item => SqSwingButton = (item: Item) =>
      new SqSwingButton(
        "Discard",
        (_: ActionEvent) => {},
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
      List(useButton(item), discardButton(item), goBackButton)
    )
  }

  private def formattedItemName(item: Item) =
    item.name + " [" + item.getClass.getSimpleName + {
      item match {
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
