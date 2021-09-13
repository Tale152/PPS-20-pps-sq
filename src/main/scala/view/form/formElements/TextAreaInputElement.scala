package view.form.formElements

import javax.swing.{JScrollPane, JTextArea}

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
