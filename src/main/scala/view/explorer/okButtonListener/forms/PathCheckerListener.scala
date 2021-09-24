package view.explorer.okButtonListener.forms

import controller.ExplorerController
import controller.prolog.structs.StructsNames.Predicates.PathPredicateName
import controller.util.ResourceNames.FileExtensions.TxtExtension
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.{TheEndingId, TheStartingId}
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form
import view.explorer.ExplorerFileTextBuilder
import view.explorer.ExplorerDialogs.FileCreatedDialog

case class PathCheckerListener(override val form: Form, override val controller: ExplorerController)
  extends ExplorerFormButtonListener(form, controller) {

  override def inputConditions: List[(Boolean, String)] =
    List(
      (NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)),
      (NonEmptyString(form.elements(1).value), mustBeSpecified(TheEndingId))
    )

  override def stateConditions: List[(Boolean, String)] = List()

  override def fileSaveCondition: () => Boolean =
    () => controller.pathExists(form.elements.head.value.toInt, form.elements(1).value.toInt)

  override def saveFile(folderPath: String): Unit = {
    val startId = form.elements.head.value.toInt
    val endId = form.elements(1).value.toInt
    val solutions = controller.paths(startId, endId)
    val filePath: String = folderPath + "/" + PathPredicateName + "(" + startId + "," + endId + ",X)." + TxtExtension
    ExplorerFileTextBuilder()
      .title("All the possible path from node " + startId + " to node " + endId)
      .addIterableOfIterables(solutions)
      .size(solutions.size)
      .outputFile(filePath)
    FileCreatedDialog(controller, filePath)
  }
}
