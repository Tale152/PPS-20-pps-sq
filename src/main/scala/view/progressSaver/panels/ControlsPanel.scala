package view.progressSaver.panels

import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingFlowPanel}

object ControlsPanel {

  /**
   * Panel contained in [[view.progressSaver.ProgressSaverView]]; renders two buttons:
   * one to actually save the progress and one to go back.
   * @param onBack strategy to be applied on back button click
   * @param onSave strategy to be applied on save button click
   */
  class ControlsPanel(onBack: Unit => Unit, onSave: Unit => Unit) extends SqSwingFlowPanel {
    this.add(SqSwingButton("Back", _ => onBack()))
    this.add(SqSwingButton("Save", _ => onSave()))
  }

  def apply(onBack: Unit => Unit, onSave: Unit => Unit): ControlsPanel = new ControlsPanel(onBack, onSave)
}
