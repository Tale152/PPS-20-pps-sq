package view.form.formElements

import view.Frame
import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingLabel}

import java.awt.Dimension
import javax.swing._

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

  override def getMinimumSize: Dimension = new Dimension(
    (Frame.getSquareDimension.width * 0.8).toInt,
    super.getMinimumSize.height
  )

  override def getMaximumSize: Dimension = new Dimension(
    getMinimumSize.width,
    super.getMaximumSize.height
  )

  override def getPreferredSize: Dimension = new Dimension(
    getMinimumSize.width,
    super.getPreferredSize.height
  )
}
