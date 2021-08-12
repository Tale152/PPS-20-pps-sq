package view.statConfiguration.panels

import java.awt.Color
import javax.swing.{JLabel, JPanel}

object InstructionPanel {

  class InstructionPanel() extends JPanel {
    this.setOpaque(false)
    private val instructionLabel: JLabel = new JLabel("Distribute points to set your character's stats")
    instructionLabel.setForeground(Color.WHITE)
    this.add(instructionLabel)
  }

  def apply(): InstructionPanel = new InstructionPanel()
}
