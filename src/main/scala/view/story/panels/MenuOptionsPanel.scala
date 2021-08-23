package view.story.panels

import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingFlowPanel}

object MenuOptionsPanel {

  class MenuOptionsPanel(onStats: Unit => Unit, onHistory: Unit => Unit, onProgressSave: Unit => Unit)
    extends SqSwingFlowPanel {
    this.add(SqSwingButton("Stats", _ => onStats()))
    this.add(SqSwingButton("History", _ => onHistory()))
    this.add(SqSwingButton("Save Progress", _ => onProgressSave()))
  }

  def apply(onStats: Unit => Unit, onHistory: Unit => Unit, onProgressSave: Unit => Unit): MenuOptionsPanel =
    new MenuOptionsPanel(onStats, onHistory, onProgressSave)
}
