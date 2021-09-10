package view.playerConfiguration.panels

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

object InstructionPanel {

  private object InstructionCommons {
    val TextSize = 17
  }

  /**
   * Panel contained in [[view.playerConfiguration.PlayerConfigurationView]]; renders instructions for the player
   */
  class InstructionPanel() extends SqSwingFlowPanel {
    import view.playerConfiguration.panels.InstructionPanel.InstructionCommons.TextSize
    this.add(SqSwingLabel("Distribute points to set your character's stats", bold = true, labelSize = TextSize))
  }

  def apply(): InstructionPanel = new InstructionPanel()
}
