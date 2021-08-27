package view.util.common

import view.util.scalaQuestSwingComponents.SqSwingButton.SqSwingButton

import java.awt.Dimension
import javax.swing.border.EmptyBorder
import javax.swing.{JScrollPane, ScrollPaneConstants}

case class ScrollableVerticalButtons(buttons: Set[SqSwingButton]) extends JScrollPane(
  VerticalButtons(buttons),
  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER) {

  private val scrollIncrement = 10

  this.setBorder(new EmptyBorder(0, 0, 0, 0))
  this.getVerticalScrollBar.setUnitIncrement(scrollIncrement)
  this.getVerticalScrollBar.setPreferredSize(new Dimension(0, 0))
}
