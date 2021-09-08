package view.history.panels

import view.util.scalaQuestSwingComponents.SqSwingBoxPanel

import javax.swing.BoxLayout

/**
 * Panel that contains a summary of every previous choice made by the player.
 *
 * @param previousChoices        a list containing every choice took by the player.
 * @param currentNodeDescription the plot description of the actual node.
 */
case class RecapPanel(previousChoices: List[(String, String)], currentNodeDescription: String)
  extends SqSwingBoxPanel(BoxLayout.Y_AXIS) {
  previousChoices.foreach(c => this.add(PreviousChoicePanel(c._1, c._2)))
  this.add(PreviousChoicePanel(currentNodeDescription))
}
