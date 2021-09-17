package view.info.okButtonListener

import controller.InfoController
import controller.prolog.structs.StructsNames.Predicates.ReachAllFinalNodesPredicateName
import controller.util.Resources.ResourceName.FileExtensions.TxtExtension
import controller.util.serialization.StringUtil.listFormattedLikeArray
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.TheStartingId
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form
import view.info.InfoFileTextBuilder
import view.info.dialog.InfoDialogs.FileCreatedDialog

case class OutcomeFromIdOkListener(override val form: Form, override val controller: InfoController)
  extends InfoOkFormButtonListener(form, controller) {

  override def inputConditions: List[(Boolean, String)] =
    List((NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)))

  override def stateConditions: List[(Boolean, String)] = List()

  override def performActionCondition: () => Boolean =
    () => controller.storyOutcomes(form.elements.head.value.toInt).nonEmpty

  override def saveFile(folderPath: String): Unit = {
    val startId = form.elements.head.value.toInt
    val solutions = controller.storyOutcomes(form.elements.head.value.toInt)
    val filePath: String = folderPath + "/" + ReachAllFinalNodesPredicateName + "(" + startId + ",X)." + TxtExtension
    val builder = InfoFileTextBuilder().title("All the possible outcome from node " + startId)
    solutions.foreach(s => builder.addRecord(listFormattedLikeArray(s)))
    builder.size(solutions.size).outputFile(filePath)
    FileCreatedDialog(controller, filePath)
  }

}
