package view.info.dialog

import controller.Controller
import view.util.scalaQuestSwingComponents.dialog.SqOkSwingDialog

object InfoDialogs {

  case class NoSolutionDialog(controller: Controller)
    extends SqOkSwingDialog(
      "No Solution",
      "No solution found for the given data.",
      _ => controller.execute()
    )

  case class FileCreatedDialog(controller: Controller, filePath: String)
    extends SqOkSwingDialog(
      "File created",
      "File created in path" + filePath,
      _ => controller.execute()
    )


}

