package controller.editor.util

import de.milchreis.uibooster.model.FormElement
import de.milchreis.uibooster.model.FormElementChangeListener

import java.awt.event.{KeyAdapter, KeyEvent}
import javax.swing.JComponent
import javax.swing.JFormattedTextField
import javax.swing.text.NumberFormatter
import java.text.NumberFormat

/**
 * [[de.milchreis.uibooster.model.Form]] used for the input of an Integer value.
 * @param labelText the Label text.
 * @param readOnly true if the value is read only.
 */
case class IntegerFormElement(labelText: String, readOnly: Boolean = false)
  extends FormElement(labelText) {

  val textField: JFormattedTextField = integerTextField()
  textField.setValue(0)
  textField.setEnabled(!readOnly)

  override def createComponent(changeListener: FormElementChangeListener): JComponent = {
    if (changeListener != null) textField.addKeyListener(new KeyAdapter() {
      override def keyReleased(e: KeyEvent): Unit = {
        super.keyReleased(e)
        changeListener.onChange(IntegerFormElement.this, getValue, form)
      }
    })
    textField
  }

  override def setEnabled(enable: Boolean): Unit = {
    textField.setEnabled(enable)
  }

  override def getValue: String = textField.getValue.toString

  override def setValue(value: Any): Unit = textField.setValue(value)

  private def integerTextField() : JFormattedTextField = {
    val format: NumberFormat = NumberFormat.getInstance
    val formatter = new NumberFormatter(format)
    formatter.setValueClass(classOf[Integer])
    formatter.setMinimum(0)
    formatter.setMaximum(Integer.MAX_VALUE)
    formatter.setAllowsInvalid(false)
    new JFormattedTextField(formatter)
  }

}
