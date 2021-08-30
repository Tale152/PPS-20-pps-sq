package view.util.common

import java.awt.{Color, Dimension}
import javax.swing.border.EmptyBorder
import javax.swing.plaf.basic.BasicScrollBarUI
import javax.swing.{JButton, JComponent, JScrollPane, ScrollPaneConstants}

case class Scrollable(component: JComponent) extends JScrollPane(
  component,
  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
) {
  private val ScrollIncrement: Int = 10
  private val ScrollbarWidth: Int = 10

  this.setBackground(Color.BLACK)
  this.setBorder(new EmptyBorder(0, 0, 0, 0))
  this.getVerticalScrollBar.setUnitIncrement(ScrollIncrement)
  this.getVerticalScrollBar.setBackground(Color.BLACK)
  this.getVerticalScrollBar.setUI(
    new BasicScrollBarUI(){
      override def configureScrollBarColors(): Unit = this.thumbColor = Color.WHITE

      override def createIncreaseButton(orientation: Int): JButton = noBtn

      override def createDecreaseButton(orientation: Int): JButton = noBtn

      private def noBtn: JButton = {
        val btn = new JButton()
        btn.setPreferredSize(new Dimension(0,0))
        btn.setMinimumSize(new Dimension(0,0))
        btn.setMaximumSize(new Dimension(0,0))
        btn
      }
    }
  )
  this.getVerticalScrollBar.setPreferredSize(new Dimension(ScrollbarWidth, 0))
}
