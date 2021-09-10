package view.story.panels

import view.util.common.Scrollable
import view.util.scalaQuestSwingComponents.SqTextArea

import javax.swing.BorderFactory

/**
 * Panel that provides narrative and node-by-node description to the user.
 *
 * @param text the actual description of each node's plot.
 */
case class NarrativePanel(text: String) extends StoryPanel("Narrative") {

  private object Paddings {
    val RightPadding: Int = 30
    val OtherPaddings: Int = 10
  }

  import Paddings._

  val textArea: SqTextArea = SqTextArea(text)
  textArea.setBorder(BorderFactory.createEmptyBorder(OtherPaddings, OtherPaddings, OtherPaddings, RightPadding))
  this.add(Scrollable(textArea))
}
