package view.story.panels

import model.nodes.Pathway
import view.Frame
import view.util.common.ScrollableVerticalButtons
import view.util.scalaQuestSwingComponents.SqSwingButton.SqSwingButton
import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingButton}

import java.awt.{Color, Dimension}
import javax.swing.border.TitledBorder
import javax.swing.{BorderFactory, BoxLayout}

case class PathwaysPanel(paths: Seq[Pathway], onPathwayChosen: Pathway => Unit)
  extends SqSwingBoxPanel(BoxLayout.X_AXIS){
  val buttons: Seq[SqSwingButton] = for(p <- paths) yield SqSwingButton(p.description, _ => onPathwayChosen(p))
  this.add(ScrollableVerticalButtons(buttons.toSet))
  val border: TitledBorder =
    BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Choose a pathway")
  border.setTitleColor(Color.WHITE)
  this.setBorder(border)

  override def getMinimumSize: Dimension =
    new Dimension(Frame.getSquareDimension.getWidth.toInt, (Frame.getSquareDimension.getHeight * 0.4).toInt)

  override def getMaximumSize: Dimension = getMinimumSize

  override def getPreferredSize: Dimension = getMinimumSize

}
