package view.playerConfiguration.panels

import view.playerConfiguration.panels.instructionCommons.TextSize
import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

private object instructionCommons {
  val TextSize = 17
}

object InstructionPanel {

  /**
   * Panel contained in [[view.playerConfiguration.PlayerConfigurationView]]; renders instructions for the player
   */
  class InstructionPanel() extends SqSwingFlowPanel {
    this.add(SqSwingLabel("Distribute points to set your character's stats", bold = true, labelSize = TextSize))
  }

  def apply(): InstructionPanel = new InstructionPanel()
}
