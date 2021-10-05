package view.explorer

import controller.Controller
import view.util.scalaQuestSwingComponents.dialog.SqOkSwingDialog

object ExplorerDialogs {

  def noSolutionDialog(controller: Controller): SqOkSwingDialog =
    SqOkSwingDialog(
      "No Solution",
      "No solution found for the given data.",
      _ => controller.execute()
    )

  def fileCreatedDialog(controller: Controller, filePath: String): SqOkSwingDialog =
    SqOkSwingDialog(
      "File created",
      "File created in path" + filePath,
      _ => controller.execute()
    )

}
