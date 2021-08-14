package view.statStatus.panels

import java.awt.Color
import javax.swing.{JLabel, JPanel}

object PlayerNamePanel {

  /**
   * Panel contained in [[view.statStatus.StatStatusView]]; renders the player's name.
   * @param name the name to render
   */
  class PlayerNamePanel(private val name: String) extends JPanel {
    this.setOpaque(false)
    private val playerNameLabel: JLabel = new JLabel("Player: " + name)
    playerNameLabel.setForeground(Color.WHITE)
    this.add(playerNameLabel)
  }

  def apply(name: String): PlayerNamePanel = new PlayerNamePanel(name)
}
