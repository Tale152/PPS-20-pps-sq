package view.story

import model.nodes.Pathway
import view.Frame
import view.util.common.{Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.SqSwingButton.SqSwingButton
import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingButton, SqTextArea}

import java.awt.{Color, Dimension}
import javax.swing.border.TitledBorder
import javax.swing.{BorderFactory, BoxLayout}

/**
 * Panel that provides story narrative and controls to the player.
 *
 * @param title the title of the section to display.
 */
abstract class StoryPanel(title: String) extends SqSwingBoxPanel(BoxLayout.Y_AXIS) {
  val border: TitledBorder =
    BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), title)
  border.setTitleColor(Color.WHITE)
  this.setBorder(border)
}

/**
 * Panel that provides narrative and node-by-node description to the user.
 *
 * @param text the actual description of each node's plot.
 */
case class NarrativePanel(text: String) extends StoryPanel("Narrative") {

  val RightPadding: Int = 30
  val Paddings: Int = 10

  val textArea: SqTextArea = SqTextArea(text)
  textArea.setBorder(BorderFactory.createEmptyBorder(Paddings, Paddings, Paddings, RightPadding))
  this.add(Scrollable(textArea))
}

/**
 * Panel that provides the user with a list of possible choises to navigate the story.
 *
 * @param paths           a sequence of all possible path to be taken in the actual node.
 * @param onPathwayChosen the selected path.
 */
case class PathwaysPanel(paths: Seq[Pathway], onPathwayChosen: Pathway => Unit)
  extends StoryPanel("Choose a pathway") {

  val buttons: Seq[SqSwingButton] = for (p <- paths) yield SqSwingButton(p.description, _ => onPathwayChosen(p))
  this.add(Scrollable(VerticalButtons(buttons.toSet)))

  override def getMinimumSize: Dimension =
    new Dimension(Frame.getSquareDimension.getWidth.toInt, (Frame.getSquareDimension.getHeight * 0.4).toInt)

  override def getMaximumSize: Dimension = getMinimumSize

  override def getPreferredSize: Dimension = getMinimumSize

}
