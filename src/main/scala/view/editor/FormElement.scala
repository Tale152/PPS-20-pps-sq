package view.editor

import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingLabel}

import javax.swing.{BoxLayout, JTextField}

abstract class FormElement extends SqSwingBoxPanel(BoxLayout.Y_AXIS) {

  def value: AnyRef

}

case class TextInputElement(textLabel: String) extends FormElement {

  val jLabel: SqSwingLabel = SqSwingLabel(textLabel)
  val jTextField: JTextField = new JTextField()
  this.add(jLabel)
  this.add(jTextField)

  override def value: AnyRef = jTextField.getText

}
