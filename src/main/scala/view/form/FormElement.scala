package view.form

import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingLabel}

import java.text.NumberFormat
import javax.swing._
import javax.swing.text.NumberFormatter

/**
 * Abstract class that describe a FormElement. Every Form Element has a label that describes it's functionality
 * and can eventually return a value.
 *
 * @param textLabel the label that describe the FormElement.
 */
abstract class FormElement(textLabel: String) extends SqSwingBoxPanel(BoxLayout.Y_AXIS) {

  private val jLabel: SqSwingLabel = SqSwingLabel(textLabel)
  this.add(jLabel)

  /**
   * @return the value of the element, for example the text in case of a TextField.
   */
  def value: String
}

/**
 * Form Element that represents a TextField.
 *
 * @param textLabel the label that describe the FormElement.
 */
case class TextInputElement(textLabel: String) extends FormElement(textLabel) {

  val jTextField: JTextField = new JTextField()
  this.add(jTextField)

  override def value: String = jTextField.getText
}

/**
 * Form Element that represents a TextAreaField.
 *
 * @param textLabel the label that describe the FormElement.
 * @param oldText   an optional string. If defined iw will be the initial text of the TextArea.
 */
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

/**
 * Form Element that represents a TextField which can only accept integer values.
 *
 * @param textLabel the label that describe the FormElement.
 */
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
