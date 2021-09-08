package view.editor

import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingLabel}

import java.text.NumberFormat
import javax.swing._
import javax.swing.text.NumberFormatter

abstract class FormElement(textLabel: String) extends SqSwingBoxPanel(BoxLayout.Y_AXIS) {

  private val jLabel: SqSwingLabel = SqSwingLabel(textLabel)
  this.add(jLabel)

  def value: String
}


case class TextInputElement(textLabel: String) extends FormElement(textLabel) {

  val jTextField: JTextField = new JTextField()
  this.add(jTextField)

  override def value: String = jTextField.getText
}

case class TextAreaInputElement(textLabel: String) extends FormElement(textLabel) {

  val jTextArea: JTextArea = new JTextArea(5, 0)
  this.add(new JScrollPane(jTextArea))

  override def value: String = jTextArea.getText
}

case class IntegerInputElement(textLabel: String) extends FormElement(textLabel) {
  val format: NumberFormat = NumberFormat.getInstance
  val formatter = new NumberFormatter(format)
  formatter.setValueClass(classOf[Integer])
  formatter.setMinimum(0)
  formatter.setMaximum(Integer.MAX_VALUE)
  formatter.setAllowsInvalid(false)
  val jFormattedTextField: JFormattedTextField = new JFormattedTextField(formatter)
  this.add(jFormattedTextField)

  override def value: String = jFormattedTextField.getText
}

case class ComboBoxElement(textLabel: String, comboElements: List[String]) extends FormElement(textLabel) {
  val comboBox = new JComboBox[String]()
  comboElements.foreach(e => comboBox.addItem(e))
  this.add(comboBox)

  override def value: String = comboBox.getSelectedItem.asInstanceOf[String]
}