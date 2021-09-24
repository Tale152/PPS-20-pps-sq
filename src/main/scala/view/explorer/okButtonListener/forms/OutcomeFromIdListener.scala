package view.explorer.okButtonListener.forms

import controller.ExplorerController
import controller.prolog.structs.StructsNames.Predicates.ReachAllFinalNodesPredicateName
import controller.util.Resources.ResourceName.FileExtensions.TxtExtension
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.TheStartingId
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.explorer.okButtonListener.ExplorerButtonListener
import view.form.Form
import view.explorer.ExplorerFileTextBuilder
import view.explorer.ExplorerDialogs.FileCreatedDialog

case class OutcomeFromIdListener(override val form: Form, override val controller: ExplorerController)
  extends ExplorerFormButtonListener(form, controller) with ExplorerButtonListener {

  override def inputConditions: List[(Boolean, String)] =
    List((NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)))

  override def stateConditions: List[(Boolean, String)] = List()

  override def fileSaveCondition: () => Boolean =
    () => controller.storyOutcomes(form.elements.head.value.toInt).nonEmpty

  override def saveFile(folderPath: String): Unit = {
    val startId = form.elements.head.value.toInt
    val solutions = controller.storyOutcomes(form.elements.head.value.toInt)
    val filePath: String = folderPath + "/" + ReachAllFinalNodesPredicateName + "(" + startId + ",X)." + TxtExtension
    ExplorerFileTextBuilder()
      .title("All the possible outcome from node " + startId)
      .addIterableOfIterables(solutions)
      .size(solutions.size)
      .outputFile(filePath)
    FileCreatedDialog(controller, filePath)
  }

}
