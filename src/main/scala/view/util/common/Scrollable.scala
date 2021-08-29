package view.util.common

import java.awt.Dimension
import javax.swing.border.EmptyBorder
import javax.swing.{JComponent, JScrollPane, ScrollPaneConstants}

case class Scrollable(component: JComponent) extends JScrollPane(
  component,
  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
) {
  private val scrollIncrement = 10

  this.setBorder(new EmptyBorder(0, 0, 0, 0))
  this.getVerticalScrollBar.setUnitIncrement(scrollIncrement)
  this.getVerticalScrollBar.setPreferredSize(new Dimension(0, 0))
}
