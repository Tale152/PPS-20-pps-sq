package view.editor.forms

import controller.editor.EditorController
import view.editor.FormConditionValues.ConditionDescriptions.{InvalidIDMessage, StoryNodeDoesNotExists}
import view.editor.FormConditionValues.Conditions.NonEmptyString
import view.editor.{Form, FormBuilder, OkFormButtonListener}
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog

import java.awt.event.ActionEvent

object DeleteStoryNode {

  def showDeleteStoryNodeForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which node you want to delete? (id)")
      .get(editorController)
    form.setOkButtonListener(new OkFormButtonListener {
      /**
       * The action to perform in case of success.
       *
       * @see [[view.editor.OkFormButtonListener#approvalCondition()]]
       */
      override def performAction(): Unit = {
        if(editorController.getStoryNode(form.elements.head.value.toInt).pathways.nonEmpty){
          SqYesNoSwingDialog("Really delete this node?",
            "All the subsequent nodes will also be deleted in cascade",
            (_ :ActionEvent) => _deleteStoryNode(),
            (_ :ActionEvent) => {})
        } else {
          _deleteStoryNode()
        }
        def _deleteStoryNode(): Unit = editorController.deleteExistingStoryNode(form.elements.head.value.toInt)

      }

      /**
       * Specify the conditions and describe them.
       * If ALL are satisfied (&&) call [[view.editor.OkFormButtonListener#performAction()]].
       *
       * @return a List containing a condition and it's textual description.
       */
      override def conditions: List[(Boolean, String)] = List(
        (NonEmptyString(form.elements.head.value), InvalidIDMessage),
        (editorController.storyNodeExists(form.elements.head.value.toInt), StoryNodeDoesNotExists),
        (form.elements.head.value.toInt != 0, "The original starting node can't be deleted")
      )
    })
    form.render()
  }

}
