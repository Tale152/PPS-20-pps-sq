package view.editor

import controller.editor.EditorController
import view.AbstractView
import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingButton}

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.BoxLayout

sealed trait Form extends AbstractView {

  def elements : List[FormElement]

  def setOkButtonListener(okButtonListener: ActionListener) : Unit
}

object Form {

  private class FormImpl(editorController: EditorController, formElements: List[FormElement]) extends Form {

    val elements: List[FormElement] = formElements
    private var okButton: SqSwingButton = SqSwingButton("Ok", (_: ActionEvent) => {})
    private val cancelButton: SqSwingButton = SqSwingButton("Cancel", (_: ActionEvent) => editorController.execute())

    /**
     * Sub-portion of render() where graphical elements are added
     */
    override def populateView(): Unit = {
      elements.foreach(e => this.add(e))
      val buttonsPanel : SqSwingBoxPanel = new SqSwingBoxPanel(BoxLayout.X_AXIS){}
      buttonsPanel.add(okButton)
      buttonsPanel.add(cancelButton)
      this.add(buttonsPanel)
    }

    override def setOkButtonListener(okButtonListener: ActionListener): Unit =
      okButton = SqSwingButton("Ok", okButtonListener)
  }

  def apply(editorController: EditorController, formElements: List[FormElement]): Form =
    new FormImpl(editorController, formElements)
}



