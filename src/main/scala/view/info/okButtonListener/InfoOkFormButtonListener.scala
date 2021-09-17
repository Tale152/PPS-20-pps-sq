package view.info.okButtonListener

import controller.InfoController
import view.form.{Form, OkFormButtonListener}
import view.util.scalaQuestSwingComponents.dialog.SqOkSwingDialog
import view.util.scalaQuestSwingComponents.fileChooser.SqSwingDirectoryChooser

/**
 * Abstract class used to structure the behaviour of all the [[view.form.OkFormButtonListener]] in the Info page.
 * @param form The parent form.
 * @param controller A InfoController.
 */
abstract class InfoOkFormButtonListener(override val form: Form, override val controller: InfoController)
  extends OkFormButtonListener(form, controller){

  /**
   * Template method used to structure the file saving operation.
   */
  override def performAction(): Unit = {
    if(performActionCondition()) {
      SqSwingDirectoryChooser(
        "Select folder where to save the file",
        folderPath => saveFile(folderPath)
      )
    } else{
      SqOkSwingDialog(
        "No Solution",
        "No solution found for the given data.",
        _ => controller.execute()
      )
    }
  }

  /**
   * @return a condition. If true, the file creation phase will be next.
   */
  def performActionCondition: () => Boolean

  /**
   * Save the file in the folder 'folderPath'.
   * @param folderPath the path of the folder chosen by the user.
   */
  def saveFile(folderPath: String): Unit

}
