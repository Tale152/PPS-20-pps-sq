package view.statConfiguration.panels

import model.characters.properties.stats.Stat

import java.awt.Color
import java.awt.event.ActionEvent
import javax.swing.{JButton, JLabel, JPanel}

object StatEditPanel {

  class StatEditPanel(private val stat: Stat,
                      private val remainingPoints: Int,
                      private val onMinus: Unit => Unit,
                      private val onPlus: Unit => Unit) extends JPanel {
    this.setOpaque(false)
    private val minusButton: JButton = new JButton("-")
    minusButton.addActionListener((_: ActionEvent) => onMinus(stat.value()))
    if (stat.value() == 1) minusButton.setEnabled(false)
    private val plusButton: JButton = new JButton("+")
    plusButton.addActionListener((_: ActionEvent) => onPlus(stat.value()))
    if (remainingPoints == 0) plusButton.setEnabled(false)
    private val statLabel: JLabel = new JLabel(stat.statName().toString + " [" + stat.value().toString + "]")
    statLabel.setForeground(Color.WHITE)
    this.add(minusButton)
    this.add(statLabel)
    this.add(plusButton)
  }

  def apply(stat: Stat, remainingPoints: Int, onMinus: Unit => Unit, onPlus: Unit => Unit): StatEditPanel =
    new StatEditPanel(stat, remainingPoints, onMinus, onPlus)
}
