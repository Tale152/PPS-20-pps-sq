package view.form

import controller.editor.EditorController
import view.AbstractView
import view.util.common.ControlsPanel
import view.util.scalaQuestSwingComponents.{SqSwingGridBagPanel, SqSwingPanel}

import java.awt.BorderLayout
import java.awt.event.ActionListener
import javax.swing.{GroupLayout, JButton}

sealed trait Form extends AbstractView {

  def elements : List[FormElement]

  def setOkButtonListener(okButtonListener: ActionListener) : Unit
}

object Form {

  private class FormImpl(editorController: EditorController, formElements: List[FormElement]) extends Form {

    val elements: List[FormElement] = formElements
    private val okButton: JButton = new JButton("")

    private val centerPanel: SqSwingGridBagPanel = new SqSwingGridBagPanel {}

    private val groupPanel: SqSwingPanel = new SqSwingPanel(){}
    val groupLayout = new GroupLayout(groupPanel)
    groupLayout.setAutoCreateGaps(true)
    groupLayout.setAutoCreateContainerGaps(true)
    groupPanel.setLayout(groupLayout)

    centerPanel.add(groupPanel)

    this.setLayout(new BorderLayout())

    private def createVerticalGroup(): GroupLayout#SequentialGroup = {
      val group = groupLayout.createSequentialGroup()
      elements.foreach(e =>
        group
          .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
          .addComponent(e, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
      )
      group
    }

    private def createHorizontalGroup(): GroupLayout#ParallelGroup = {
      val group = groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
      elements.foreach(e =>
        group.addComponent(e, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
      )
      group
    }

    override def populateView(): Unit = {
      groupLayout.setHorizontalGroup(createHorizontalGroup())
      groupLayout.setVerticalGroup(createVerticalGroup())
      this.add(centerPanel, BorderLayout.CENTER)
      this.add(
        ControlsPanel(List(
          ("o", ("[O] OK", _ => okButton.doClick())),
          ("b", ("[B] Back", _ => editorController.execute()))
        )),
        BorderLayout.SOUTH
      )
    }

    override def setOkButtonListener(okButtonListener: ActionListener): Unit = {
      okButton.addActionListener(okButtonListener)
    }
  }

  def apply(editorController: EditorController, formElements: List[FormElement]): Form =
    new FormImpl(editorController, formElements)
}



