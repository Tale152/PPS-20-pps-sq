package view.history.panels

import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingLabel}

import javax.swing.BoxLayout

object PreviousChoicePanel {

  /**
   * Panel contained in [[view.history.HistoryView]]; renders a previous choice taken by the player.
   * @param nodeNarrative the narrative of the node associated with this choice
   * @param chosenPathwayDescription the description of the pathway chosen by the player
   */
  class PreviousChoicePanel(nodeNarrative: String, chosenPathwayDescription: String)
    extends SqSwingBoxPanel(BoxLayout.Y_AXIS){
    this.add(SqSwingLabel(nodeNarrative))
    this.add(SqSwingLabel("=> " + chosenPathwayDescription))
  }

  def apply(nodeNarrative: String, choiceDescription: String): PreviousChoicePanel =
    new PreviousChoicePanel(nodeNarrative, choiceDescription)
}
