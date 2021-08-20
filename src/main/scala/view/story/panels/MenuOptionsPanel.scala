package view.story.panels

import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingFlowPanel}

object MenuOptionsPanel {

  class MenuOptionsPanel(onStats: Unit => Unit, onHistory: Unit => Unit) extends SqSwingFlowPanel {
    this.add(SqSwingButton("Stats", _ => onStats()))
    this.add(SqSwingButton("History", _ => onHistory()))
  }

  def apply(onStats: Unit => Unit, onHistory: Unit => Unit): MenuOptionsPanel = new MenuOptionsPanel(onStats, onHistory)
}
