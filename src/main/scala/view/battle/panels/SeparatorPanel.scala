package view.battle.panels

import javax.swing.{BorderFactory, JSeparator}
import view.util.scalaQuestSwingComponents.SqSwingBorderPanel

/**
 * A separator with a border set on top and bottom.
 */
case class SeparatorPanel() extends SqSwingBorderPanel {
  val border: Int = 20
  this.add(new JSeparator())
  this.setBorder(BorderFactory.createEmptyBorder(border,0, border, 0))
}
