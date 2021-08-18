package view.history.panels

import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingLabel}

import javax.swing.BoxLayout

object PreviousChoicePanel {

  class PreviousChoicePanel(nodeNarrative: String, choiceDescription: String) extends SqSwingBoxPanel(BoxLayout.Y_AXIS){
    this.add(SqSwingLabel(nodeNarrative))
    this.add(SqSwingLabel("=> " + choiceDescription))
  }

  def apply(nodeNarrative: String, choiceDescription: String): PreviousChoicePanel =
    new PreviousChoicePanel(nodeNarrative, choiceDescription)
}
