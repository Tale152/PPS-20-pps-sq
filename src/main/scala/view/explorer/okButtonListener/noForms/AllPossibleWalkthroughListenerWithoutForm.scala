package view.explorer.okButtonListener.noForms

import controller.ExplorerController
import controller.prolog.structs.StructsNames.Predicates.AllStoryWalkthroughPredicateName
import controller.util.ResourceNames.FileExtensions.TxtExtension
import view.explorer.ExplorerFileTextBuilder
import view.explorer.ExplorerDialogs.FileCreatedDialog

case class AllPossibleWalkthroughListenerWithoutForm(override val explorerController: ExplorerController)
  extends AllPossibleGenericListenerWithoutForm[String](explorerController) {

  override def solutions: List[List[String]] = explorerController.allStoryWalkthrough

  override def saveFile(folderPath: String): Unit = {
    val filePath: String = folderPath + "/" + AllStoryWalkthroughPredicateName + "(0,X)." + TxtExtension
    ExplorerFileTextBuilder()
      .title("All the possible story Walkthrough.")
      .addStories(solutions)
      .size(solutions.size)
      .outputFile(filePath)
    FileCreatedDialog(explorerController, filePath)
  }
}
