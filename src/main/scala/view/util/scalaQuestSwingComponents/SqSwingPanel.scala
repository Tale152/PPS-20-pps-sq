package view.util.scalaQuestSwingComponents

import java.awt.{BorderLayout, Color, FlowLayout, GridBagLayout, GridLayout}
import javax.swing.{BoxLayout, JPanel}

/**
 * An abstract class that returns a custom panel with black background.
 * @see [[javax.swing.JPanel]]
 */
abstract class SqSwingPanel() extends JPanel {
  this.setBackground(Color.BLACK)
}

/**
 * An abstract class that returns a custom panel with flow layout set.
 * @see [[java.awt.FlowLayout]]
 */
abstract class SqSwingFlowPanel() extends SqSwingPanel {
  this.setLayout(new FlowLayout)
}

/**
 * An abstract class that returns a custom panel with a box layout set.
 * @param axis is the axis used to orient the elements added in the panel.
 * @see [[javax.swing.BoxLayout]]
 */
abstract class SqSwingBoxPanel(axis: Int) extends SqSwingPanel {
  this.setLayout(new BoxLayout(this, axis))
}

/**
 * An abstract class that returns a custom panel with a grid layout set.
 * @param rows is the number of rows in the panel.
 * @param cols is the number of cols in the panel.
 * @see [[java.awt.GridLayout]]
 */
abstract class SqSwingGridPanel(rows: Int, cols: Int) extends SqSwingPanel {
  this.setLayout(new GridLayout(rows, cols))
}

/**
 * An abstract class that returns a custom panel with a border layout set.
 * @see [[java.awt.BorderLayout]]
 */
abstract class SqSwingBorderPanel() extends SqSwingPanel {
  this.setLayout(new BorderLayout)
}

/**
 * An abstract class that returns a custom panel with a grid bag layout set.
 * @see [[java.awt.GridBagLayout]]
 */
abstract class SqSwingGridBagPanel() extends SqSwingPanel {
  this.setLayout(new GridBagLayout)
}
