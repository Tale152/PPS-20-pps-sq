package view.statStatus.panels

import model.characters.properties.stats.StatName.StatName

import java.awt.Color
import javax.swing.{JLabel, JPanel}

object StatValuePanel {

  /**
   * Panel contained int [[view.statStatus.StatStatusView]].
   * Renders the current stat value; if current stat value is higher than the original value it will be displayed green,
   * red if lower, white otherwise.
   * @param stat a pair with the StatName associated to a pair of original stat value and current stat value
   */
  class StatValuePanel(private val stat: (StatName, (Int, Int)))
    extends JPanel{
    this.setOpaque(false)
    private val statNameBeginLabel: JLabel = new JLabel(stat._1.toString + "[")
    statNameBeginLabel.setForeground(Color.WHITE)
    private val statValueLabel: JLabel = new JLabel(stat._2._2.toString)
    if(stat._2._2 > stat._2._1) { statValueLabel.setForeground(Color.GREEN) }
    else if (stat._2._2 < stat._2._1) { statValueLabel.setForeground(Color.RED) }
    else { statValueLabel.setForeground(Color.WHITE) }
    private val statNameEndLabel: JLabel = new JLabel("]")
    statNameEndLabel.setForeground(Color.WHITE)
    this.add(statNameBeginLabel)
    this.add(statValueLabel)
    this.add(statNameEndLabel)
  }

  def apply(stat: (StatName, (Int, Int))): StatValuePanel = new StatValuePanel(stat)
}
