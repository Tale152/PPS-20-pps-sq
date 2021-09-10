package view.inventory.panels.inventoryPanel

import controller.game.subcontroller.InventoryController
import model.characters.Character
import model.items.{EquipItem, Item, KeyItem}
import view.inventory.panels.TargetChooserDialog
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog

import java.awt.event.{ActionEvent, ActionListener}

/**
 * Used to specify the actions of the buttorn in [[view.inventory.panels.inventoryPanel.InventoryPanel]].
 */
private[inventoryPanel] object InventoryPanelButtons {

  /**
   * Specifies behavior of use Button.
   * @param inventoryController the controller tha handle the inventory,
   *                            used to check the inventory status and take actions.
   * @param item the item that is going to be used.
   * @return a custom button with the specified behavior.
   */
  def useButton(inventoryController: InventoryController, item: Item): SqSwingButton = {
    val useButtonName = item match {
      case equipItem: EquipItem if inventoryController.isEquipped(equipItem) => "Unequip"
      case _: EquipItem => "Equip"
      case _ => "Use"
    }

    val useButtonActionListener: ActionListener = {
      (_: ActionEvent) => {
        val targets: Set[Character] = inventoryController.targets()
        if (inventoryController.targets().size == 1 || item.isInstanceOf[EquipItem]) {
          inventoryController.use(item)(targets.head)
        } else {
          TargetChooserDialog(inventoryController, item, targets)
        }
      }
    }

    SqSwingButton(
      useButtonName,
      useButtonActionListener, item match {
        case _: KeyItem => false
        case _ => true
      })
    }

  /**
   * Specifies the behavior of the discard Button.
   * @param inventoryController the controller tha handle the inventory,
   *                            used to check the inventory status and take actions.
   * @param item the item that is going to be discarded.
   * @return a custom button with the specified behavior.
   */
    def discardButton(inventoryController: InventoryController, item: Item): SqSwingButton =
      SqSwingButton(
        "Discard",
        (_: ActionEvent) => {
          SqYesNoSwingDialog("Really discard?",
            "Do you really want to discard this Item?\n There's no coming back.",
            (_: ActionEvent) => inventoryController.discard(item)
          )
        })

  /**
   * Specifies the behavior of the back Button, that allows the user to exit the inventory.
   * @return a custom button with the specified behavior.
   */
  def goBackButton(): SqSwingButton =
    SqSwingButton(
      "Go Back",
      (_: ActionEvent) => {})
}
