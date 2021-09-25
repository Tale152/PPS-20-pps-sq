package view.explorer.okButtonListener.noForms

import controller.ExplorerController
import controller.prolog.structs.StructsNames.Predicates.AllFinalNodesSolutionsPredicateName
import controller.util.ResourceNames.FileExtensions.TxtExtension
import view.explorer.ExplorerFileTextBuilder
import view.explorer.ExplorerDialogs.FileCreatedDialog

case class AllPossibleOutcomeListenerWithoutForm(override val explorerController: ExplorerController)
  extends AllPossibleGenericListenerWithoutForm[Int](explorerController) {

  override def solutions: List[List[Int]] = explorerController.allStoryOutcomes

  override def saveFile(folderPath: String): Unit = {
    val filePath: String = folderPath + "/" + AllFinalNodesSolutionsPredicateName + "(0,X)." + TxtExtension
    ExplorerFileTextBuilder()
      .title("All the possible story outcomes.")
      .addIterableOfIterables(solutions)
      .size(solutions.size)
      .outputFile(filePath)
    FileCreatedDialog(explorerController, filePath)
  }
}
