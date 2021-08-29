package view.story.panels

import view.util.common.Scrollable
import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqTextArea}

import java.awt.Color
import javax.swing.{BorderFactory, BoxLayout}
import javax.swing.border.TitledBorder

case class NarrativePanel(text: String) extends SqSwingBoxPanel(BoxLayout.Y_AXIS) {

  val RightPadding: Int = 30
  val Paddings: Int = 10

  val border: TitledBorder =
    BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Narrative")
  border.setTitleColor(Color.WHITE)
  this.setBorder(border)
  val textArea: SqTextArea = SqTextArea(text)
  textArea.setBorder(BorderFactory.createEmptyBorder(Paddings, Paddings, Paddings, RightPadding))
  this.add(Scrollable(textArea))
}
