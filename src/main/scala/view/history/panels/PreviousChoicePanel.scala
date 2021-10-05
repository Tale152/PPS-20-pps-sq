package view.history.panels

import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingTextArea}

import java.awt.Color
import javax.swing.{BorderFactory, BoxLayout}

/**
 * Panel contained in [[view.history.HistoryView]]; renders a previous choice taken by the player.
 * @param nodeNarrative the narrative of the node associated with this choice.
 * @param chosenPathwayDescription the description of the pathway chosen by the player.
 */
case class PreviousChoicePanel(nodeNarrative: String, chosenPathwayDescription: String = "")
  extends SqSwingBoxPanel(BoxLayout.Y_AXIS){

  private object PreviousChoicePanelValues {
    val DefaultBorder: Int = 5
    val RightBorder: Int = 15
  }

  this.add(SqSwingTextArea(nodeNarrative))
  val chosenPathway: SqSwingTextArea = SqSwingTextArea(chosenPathwayDescription)
  chosenPathway.setForeground(Color.BLUE)
  this.add(chosenPathway)

  import PreviousChoicePanelValues._
  this.setBorder(BorderFactory.createEmptyBorder(DefaultBorder, DefaultBorder, DefaultBorder, RightBorder))
}
