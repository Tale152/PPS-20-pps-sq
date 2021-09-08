package view.editor

import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingLabel}

import java.text.NumberFormat
import javax.swing.text.NumberFormatter
import javax.swing.{BoxLayout, JFormattedTextField, JScrollPane, JTextArea, JTextField}

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

case class TextAreaInputElement(textLabel: String, oldText: String = "") extends FormElement(textLabel) {

  private object Dimensions {
    val Rows: Int = 5
    val Cols: Int = 5
  }
  import Dimensions._
  val jTextArea: JTextArea = new JTextArea(Rows, Cols)
  jTextArea.setText(oldText)
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
