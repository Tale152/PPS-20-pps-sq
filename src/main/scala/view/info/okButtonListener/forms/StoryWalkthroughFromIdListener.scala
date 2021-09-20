package view.info.okButtonListener.forms

import controller.InfoController
import controller.prolog.structs.StructsNames.Predicates.StoryWalkthroughPredicateName
import controller.util.Resources.ResourceName.FileExtensions.TxtExtension
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.TheStartingId
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form
import view.info.InfoFileTextBuilder
import view.info.dialog.InfoDialogs.FileCreatedDialog

case class StoryWalkthroughFromIdListener(override val form: Form, override val controller: InfoController)
  extends InfoFormButtonListener(form, controller) {

  override def inputConditions: List[(Boolean, String)] =
    List((NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)))

  override def stateConditions: List[(Boolean, String)] = List()

  override def fileSaveCondition: () => Boolean =
    () => controller.storyWalkthrough(form.elements.head.value.toInt).nonEmpty

  override def saveFile(folderPath: String): Unit = {
    val storyNodeId: Int = form.elements.head.value.toInt
    val solutions: Stream[List[String]] = controller.storyWalkthrough(storyNodeId)
    val filePath: String =
      folderPath + "/" + StoryWalkthroughPredicateName + "(" + storyNodeId + ",X)." + TxtExtension
    InfoFileTextBuilder()
      .title("All the story Walkthrough starting from node " + storyNodeId + ".")
      .addIterableOfIterables(solutions)
      .size(solutions.size)
      .outputFile(filePath)
    FileCreatedDialog(controller, filePath)
  }

}
