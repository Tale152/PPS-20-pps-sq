package view.inventory.panels

import controller.game.subcontroller.InventoryController
import model.characters.Character
import model.items.Item
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

import java.awt.event.ActionEvent

/**
 * Dialog that allows the user to select the target when he use an item in his inventory.
 */
object TargetChooser {

  private class TargetChooser(inventoryController: InventoryController,
                              item: Item,
                              targets: Set[Character]) extends SqSwingDialog(
    "Choose a Target",
    "Who is the target of the item?",
    targets.map(c => SqSwingButton(
      c.name + " (" + c.getClass.getSimpleName + ")",
      (_: ActionEvent) => inventoryController.use(item)(c)
    )).toList
  )

  def apply(inventoryController: InventoryController,
            item: Item,
            targets: Set[Character]): SqSwingDialog = {
    new TargetChooser(inventoryController, item, targets)
  }
}
