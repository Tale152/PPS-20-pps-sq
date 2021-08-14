package view.statStatus.panels

import java.awt.Color
import javax.swing.{JLabel, JPanel}

object HealthPanel {

  /**
   * Panel contained in [[view.statStatus.StatStatusView]]; renders the player's health.
   * @param health a pair containing current health and max health
   */
  class HealthPanel(private val health: (Int, Int)) extends JPanel {
    this.setOpaque(false)
    val healthLabel = new JLabel("Health: " + health._1 + "/" + health._2)
    healthLabel.setForeground(Color.WHITE)
    this.add(healthLabel)
  }

  def apply(health: (Int, Int)): HealthPanel = new HealthPanel(health)
}
