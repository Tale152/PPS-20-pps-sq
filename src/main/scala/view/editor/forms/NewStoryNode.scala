package view.editor.forms

import controller.editor.EditorController
import view.editor.{AbstractOkButtonListener, Form, FormBuilder}

object NewStoryNode {

  def showNewStoryNodeForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which story node is the starting node? (id)")
      .addTextField("What description should the pathway to the new story node show?<")
      .addTextField("What narrative should the new story node show?")
      .get(editorController)
    form.setOkButtonListener(
      new AbstractOkButtonListener {
        /**
         * The action to perform in case of success.
         *
         * @see [[view.editor.AbstractOkButtonListener#approvalCondition()]]
         */
         override def performAction(): Unit = {
            editorController.addNewStoryNode(
              form.elements.head.value.toInt,
              form.elements(1).value,
              form.elements(2).value
            )
         }

        /**
         * Specify the conditions and describe them.
         * If ALL are satisfied (&&) call [[view.editor.AbstractOkButtonListener#performAction()]].
         *
         * @return a List containing a condition and it's textual description.
         */
        override def conditions: List[(Boolean, String)] = {
          List(
            (form.elements.head.value.trim.nonEmpty, "An ID should be specified."),
            (form.elements(1).value.trim.nonEmpty, "A description should be specified."),
            (form.elements(2).value.trim.nonEmpty,"A narrative should be specified.")
          )
        }
      })
    form.render()
  }


}
