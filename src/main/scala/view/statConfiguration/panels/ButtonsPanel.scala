package view.statConfiguration.panels

import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingFlowPanel}

object ButtonsPanel {

  /**
   * Panel contained in [[view.statConfiguration.StatConfigurationView]]; renders two button:
   * one to confirm and one to go back
   * @param onBack strategy to be applied on back button click
   * @param onConfirm strategy to be applied on confirm button click
   */
  class ButtonsPanel(onBack: Unit => Unit, onConfirm: Unit => Unit) extends SqSwingFlowPanel{
    this.add(SqSwingButton("Back", _ => onBack()))
    this.add(SqSwingButton("Confirm", _ => onConfirm()))
  }

  def apply(onBack: Unit => Unit, onContinue: Unit => Unit): ButtonsPanel = new ButtonsPanel(onBack, onContinue)

}
