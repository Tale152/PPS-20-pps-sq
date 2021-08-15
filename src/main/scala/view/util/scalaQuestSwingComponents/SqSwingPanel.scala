package view.util.scalaQuestSwingComponents

import java.awt.{Color, FlowLayout}
import javax.swing.{BoxLayout, JPanel}

abstract class SqSwingPanel() extends JPanel {
  this.setBackground(Color.BLACK)
}

abstract class SqSwingFlowPanel() extends SqSwingPanel {
  this.setLayout(new FlowLayout())
}

abstract class SqSwingBoxPanel(axis: Int) extends SqSwingPanel {
  this.setLayout(new BoxLayout(this, axis))
}
