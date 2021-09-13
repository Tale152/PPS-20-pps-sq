package view.story.panels

import model.nodes.Pathway
import view.Frame
import view.util.common.{Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.SqSwingButton
import java.awt.Dimension

/**
 * Panel that provides the user with a list of possible choices to navigate the story.
 *
 * @param paths           a sequence of all possible path to be taken in the actual node.
 * @param onPathwayChosen the selected path.
 */
case class PathwaysPanel(paths: Seq[Pathway], onPathwayChosen: Pathway => Unit)
  extends StoryPanel("Choose a pathway") {

  val buttons: Seq[SqSwingButton] = for (p <- paths) yield SqSwingButton(p.description, _ => onPathwayChosen(p))
  this.add(Scrollable(VerticalButtons(buttons.toList)))

  override def getMinimumSize: Dimension =
    new Dimension(Frame.getSquareDimension.getWidth.toInt, (Frame.getSquareDimension.getHeight * 0.4).toInt)

  override def getMaximumSize: Dimension = getMinimumSize

  override def getPreferredSize: Dimension = getMinimumSize

}
