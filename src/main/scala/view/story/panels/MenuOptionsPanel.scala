package view.story.panels

import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingFlowPanel}

object MenuOptionsPanel {

  class MenuOptionsPanel(onStats: Unit => Unit) extends SqSwingFlowPanel {
    this.add(SqSwingButton("Stats", _ => onStats()))
  }

  def apply(onStats: Unit => Unit): MenuOptionsPanel = new MenuOptionsPanel(onStats)
}
