package view.history.panels

import view.util.scalaQuestSwingComponents.SqSwingBoxPanel

import javax.swing.BoxLayout

case class RecapPanel(previousChoices: List[(String, String)], currentNodeDescription: String)
  extends SqSwingBoxPanel(BoxLayout.Y_AXIS){
  previousChoices.foreach(c => this.add(PreviousChoicePanel(c._1, c._2)))
  this.add(PreviousChoicePanel(currentNodeDescription))
}
