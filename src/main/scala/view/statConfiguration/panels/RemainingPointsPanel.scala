package view.statConfiguration.panels

import java.awt.Color
import javax.swing.{JLabel, JPanel}

object RemainingPointsPanel {

  /**
   * Panel contained in [[view.statConfiguration.StatConfigurationView]]; renders a label containing the number
   * of remaining points. If said number has reached zero it will be printed in red, green otherwise.
   * @param remainingPoints number of remaining points to render
   */
  class RemainingPointsPanel(val remainingPoints: Int) extends JPanel {
    this.setOpaque(false)
    private val textLabel: JLabel = new JLabel("Remaining points:")
    textLabel.setForeground(Color.WHITE)
    private val pointsLabel: JLabel = new JLabel(remainingPoints.toString)
    if (remainingPoints > 0) {
      pointsLabel.setForeground(Color.GREEN)
    } else {
      pointsLabel.setForeground(Color.RED)
    }
    this.add(textLabel)
    this.add(pointsLabel)
  }

  def apply(remainingPoints: Int): RemainingPointsPanel = new RemainingPointsPanel(remainingPoints)

}