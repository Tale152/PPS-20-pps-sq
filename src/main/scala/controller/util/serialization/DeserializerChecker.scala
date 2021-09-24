package controller.util.serialization

import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

object DeserializerChecker {

  def checkOnLoadingFile(action: () => Unit, dialogTitle: String): Unit = {
    try {
      action()
    } catch {
      case _: Exception =>
        SqSwingDialog(
          dialogTitle, "File structure is not suitable or corrupted",
          List(SqSwingButton("ok", _ => {}))
        )
    }
  }

}
