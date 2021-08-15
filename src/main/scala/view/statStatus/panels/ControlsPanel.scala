package view.statStatus.panels

import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingFlowPanel}

object ControlsPanel {

  /**
   * Panel contained in [[view.statStatus.StatStatusView]]; renders one button to go back.
   * @param onBack strategy to be applied on back button click
   */
  class ControlsPanel(onBack: Unit => Unit) extends SqSwingFlowPanel {
    this.add(SqSwingButton("Back", _ => onBack()))
  }

  def apply(onBack: Unit => Unit): ControlsPanel = new ControlsPanel(onBack)
}
