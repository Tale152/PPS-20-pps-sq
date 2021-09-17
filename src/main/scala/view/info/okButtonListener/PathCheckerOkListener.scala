package view.info.okButtonListener

import controller.InfoController
import controller.prolog.structs.StructsNames.Predicates.PathPredicateName
import controller.util.Resources.ResourceName.FileExtensions.TxtExtension
import controller.util.serialization.StringUtil.listFormattedLikeArray
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.{TheEndingId, TheStartingId}
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form
import view.info.InfoFileTextBuilder
import view.info.dialog.InfoDialogs.FileCreatedDialog

case class PathCheckerOkListener(override val form: Form, override val controller: InfoController)
  extends InfoOkFormButtonListener(form, controller) {

  override def inputConditions: List[(Boolean, String)] =
    List(
      (NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)),
      (NonEmptyString(form.elements(1).value), mustBeSpecified(TheEndingId))
    )

  override def stateConditions: List[(Boolean, String)] = List()

  override def performActionCondition: () => Boolean =
    () => controller.pathExists(form.elements.head.value.toInt, form.elements(1).value.toInt)

  override def saveFile(folderPath: String): Unit = {
    val startId = form.elements.head.value.toInt
    val endId = form.elements(1).value.toInt
    val solutions = controller.paths(startId, endId)
    val filePath: String = folderPath + "/" + PathPredicateName + "(" + startId + "," + endId + ",X)." + TxtExtension
    val builder = InfoFileTextBuilder().title("All the possible path from node " + startId + " to node " + endId)
    solutions.foreach(s => builder.addRecord(listFormattedLikeArray(s)))
    builder.size(solutions.size).outputFile(filePath)
    FileCreatedDialog(controller, filePath)
  }
}
