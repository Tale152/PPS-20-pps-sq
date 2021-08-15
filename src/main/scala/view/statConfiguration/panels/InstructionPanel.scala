package view.statConfiguration.panels

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

object InstructionPanel {

  /**
   * Panel contained in [[view.statConfiguration.StatConfigurationView]]; renders instructions for the player
   */
  class InstructionPanel() extends SqSwingFlowPanel {
    this.add(SqSwingLabel("Distribute points to set your character's stats"))
  }

  def apply(): InstructionPanel = new InstructionPanel()
}
