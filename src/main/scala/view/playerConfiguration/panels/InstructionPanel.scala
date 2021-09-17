package view.playerConfiguration.panels

import view.util.StringUtil.DefaultFontSize
import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

/**
 * Panel contained in [[view.playerConfiguration.PlayerConfigurationView]]; renders instructions for the player
 */
case class InstructionPanel() extends SqSwingFlowPanel {
  this.add(SqSwingLabel("Distribute points to set your character's stats", bold = true, labelSize = DefaultFontSize))
}
