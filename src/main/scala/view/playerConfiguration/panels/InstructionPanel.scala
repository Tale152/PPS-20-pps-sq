package view.playerConfiguration.panels

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

object InstructionPanel {

  /**
   * Panel contained in [[view.playerConfiguration.PlayerConfigurationView]]; renders instructions for the player
   */
  class InstructionPanel() extends SqSwingFlowPanel {
    private val textSize = 17
    this.add(SqSwingLabel("Distribute points to set your character's stats", bold = true, size = textSize))
  }

  def apply(): InstructionPanel = new InstructionPanel()
}
