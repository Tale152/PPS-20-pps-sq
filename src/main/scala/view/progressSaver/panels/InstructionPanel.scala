package view.progressSaver.panels

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

object InstructionPanel {

  /**
   * Panel contained in [[view.progressSaver.ProgressSaverView]]; renders instructions for the player
   */
  class InstructionPanel() extends SqSwingFlowPanel {
    this.add(SqSwingLabel("Do you want to save your progress in this story?"))
  }

  def apply(): InstructionPanel = new InstructionPanel()
}
