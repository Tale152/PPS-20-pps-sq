package view.story

import model.nodes.Pathway
import view.Frame
import view.util.common.{Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingButton, SqTextArea}

import java.awt.{Color, Dimension}
import javax.swing.border.TitledBorder
import javax.swing.{BorderFactory, BoxLayout}

abstract class StoryPanel(title: String) extends SqSwingBoxPanel(BoxLayout.Y_AXIS){
  val border: TitledBorder =
    BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), title)
  border.setTitleColor(Color.WHITE)
  this.setBorder(border)
}

case class NarrativePanel(text: String) extends StoryPanel("Narrative") {

  val RightPadding: Int = 30
  val Paddings: Int = 10

  val textArea: SqTextArea = SqTextArea(text)
  textArea.setBorder(BorderFactory.createEmptyBorder(Paddings, Paddings, Paddings, RightPadding))
  this.add(Scrollable(textArea))
}

case class PathwaysPanel(paths: Seq[Pathway], onPathwayChosen: Pathway => Unit)
  extends StoryPanel("Choose a pathway") {

  val buttons: Seq[SqSwingButton] = for(p <- paths) yield SqSwingButton(p.description, _ => onPathwayChosen(p))
  this.add(Scrollable(VerticalButtons(buttons.toSet)))

  override def getMinimumSize: Dimension =
    new Dimension(Frame.getSquareDimension.getWidth.toInt, (Frame.getSquareDimension.getHeight * 0.4).toInt)

  override def getMaximumSize: Dimension = getMinimumSize

  override def getPreferredSize: Dimension = getMinimumSize

}
