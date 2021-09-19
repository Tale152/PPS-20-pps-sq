package view.info.okButtonListener

import controller.InfoController
import controller.prolog.structs.StructsNames.Predicates.StoryWalkthroughPredicateName
import controller.util.Resources.ResourceName.FileExtensions.TxtExtension
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.TheStartingId
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form
import view.info.InfoFileTextBuilder
import view.info.dialog.InfoDialogs.FileCreatedDialog

case class StoryWalkthroughFromIdOkListener(override val form: Form, override val controller: InfoController)
  extends InfoOkFormButtonListener(form, controller) {

  override def inputConditions: List[(Boolean, String)] =
    List((NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)))

  override def stateConditions: List[(Boolean, String)] = List()

  override def performActionCondition: () => Boolean = {
    () => controller.storyWalkthrough(form.elements.head.value.toInt).nonEmpty
  }

  override def saveFile(folderPath: String): Unit = {
    val storyNodeId: Int = form.elements.head.value.toInt
    val solutions: Stream[List[String]] = controller.storyWalkthrough(storyNodeId)
    val filePath: String =
      folderPath + "/" + StoryWalkthroughPredicateName + "(" + storyNodeId + ",X)." + TxtExtension
    val builder = InfoFileTextBuilder().title("All the story Walkthrough starting from node " + storyNodeId + ".")
    solutions.foreach(s0 => {
      builder.addRecord("-------STORY START-------")
      s0.foreach(s1 => builder.addRecord(s1))
      builder.addRecord("--------STORY END--------\n")
    })
    builder.size(solutions.size).outputFile(filePath)
    FileCreatedDialog(controller, filePath)
  }

}
