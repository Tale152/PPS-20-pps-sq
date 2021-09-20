package view.info.okButtonListener.noForms

import controller.InfoController
import controller.prolog.structs.StructsNames.Predicates.AllFinalNodesSolutionsPredicateName
import controller.util.Resources.ResourceName.FileExtensions.TxtExtension
import view.info.InfoFileTextBuilder
import view.info.dialog.InfoDialogs.FileCreatedDialog

case class AllPossibleOutcomeListenerWithoutForm(override val infoController: InfoController)
  extends AllPossibleGenericListenerWithoutForm[Int](infoController) {

  override def solutions: List[List[Int]] = infoController.allStoryOutcomes

  override def saveFile(folderPath: String): Unit = {
    val filePath: String = folderPath + "/" + AllFinalNodesSolutionsPredicateName + "(0,X)." + TxtExtension
    InfoFileTextBuilder()
      .title("All the possible story outcomes.")
      .addIterableOfIterables(solutions)
      .size(solutions.size)
      .outputFile(filePath)
    FileCreatedDialog(infoController, filePath)
  }
}
