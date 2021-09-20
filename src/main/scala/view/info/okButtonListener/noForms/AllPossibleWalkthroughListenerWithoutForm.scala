package view.info.okButtonListener.noForms

import controller.InfoController
import controller.prolog.structs.StructsNames.Predicates.AllStoryWalkthroughPredicateName
import controller.util.Resources.ResourceName.FileExtensions.TxtExtension
import view.info.InfoFileTextBuilder
import view.info.dialog.InfoDialogs.FileCreatedDialog

case class AllPossibleWalkthroughListenerWithoutForm(override val infoController: InfoController)
  extends AllPossibleGenericListenerWithoutForm[String](infoController) {

  override def solutions: List[List[String]] = infoController.allStoryWalkthrough

  override def saveFile(folderPath: String): Unit = {
    val filePath: String = folderPath + "/" + AllStoryWalkthroughPredicateName + "(0,X)." + TxtExtension
    InfoFileTextBuilder()
      .title("All the possible story Walkthrough.")
      .addStories(solutions)
      .size(solutions.size)
      .outputFile(filePath)
    FileCreatedDialog(infoController, filePath)
  }
}
