package view.util.scalaQuestSwingComponents

import java.awt.{Color, FlowLayout, GridLayout}
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

abstract class SqSwingGridPanel(rows: Int, cols: Int) extends SqSwingPanel {
  this.setLayout(new GridLayout(rows, cols))
}
