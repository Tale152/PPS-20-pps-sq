package view.history.panels

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

import java.awt.Color

object CurrentNodePanel {

  /**
   * Panel contained in [[view.history.HistoryView]]; renders the current node in the story.
   * @param narrative the narrative of the current node in the story
   */
  class CurrentNodePanel(narrative: String) extends SqSwingFlowPanel {
    this.add(SqSwingLabel(narrative, Color.BLUE))
  }

  def apply(narrative: String): CurrentNodePanel = new CurrentNodePanel(narrative)
}
