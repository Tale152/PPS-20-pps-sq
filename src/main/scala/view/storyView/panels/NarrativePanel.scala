package view.storyView.panels

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

object NarrativePanel {
  class NarrativePanel(text: String) extends SqSwingFlowPanel {
    this.add(SqSwingLabel(text))
  }

  def apply(text: String): NarrativePanel = new NarrativePanel(text)
}
