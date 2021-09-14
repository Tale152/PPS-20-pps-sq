package view.form

import controller.Controller
import view.AbstractView
import view.form.formElements.FormElement
import view.util.common.{ControlsPanel, Scrollable}
import view.util.scalaQuestSwingComponents.{SqSwingGridBagPanel, SqSwingPanel}

import java.awt.BorderLayout
import java.awt.event.ActionListener
import javax.swing.{GroupLayout, JButton}

/**
 * Trait that represents a Form. A view that contains multiple elements. The user may interact with them.
 */
sealed trait Form extends AbstractView {

  /**
   * @return a list containing all the [[FormElement]] of the Form.
   */
  def elements: List[FormElement]

  /**
   * Set the Action listener of the confirmation button.
   *
   * @param okButtonListener the action Listener of the Ok button.
   */
  def setOkButtonListener(okButtonListener: ActionListener): Unit
}

object Form {

  private class FormImpl(controller: Controller, formElements: List[FormElement]) extends Form {

    val elements: List[FormElement] = formElements
    private val okButton: JButton = new JButton("")

    private val centerPanel: SqSwingGridBagPanel = new SqSwingGridBagPanel {}

    private val groupPanel: SqSwingPanel = new SqSwingPanel() {}
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
      this.add(Scrollable(centerPanel), BorderLayout.CENTER)
      this.add(
        ControlsPanel(List(
          ("o", ("[O] OK", _ => okButton.doClick())),
          ("b", ("[B] Back", _ => controller.execute()))
        )),
        BorderLayout.SOUTH
      )
    }

    override def setOkButtonListener(okButtonListener: ActionListener): Unit = {
      okButton.addActionListener(okButtonListener)
    }
  }

  def apply(controller: Controller, formElements: List[FormElement]): Form =
    new FormImpl(controller, formElements)
}



