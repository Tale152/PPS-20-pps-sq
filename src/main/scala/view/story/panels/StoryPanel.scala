package view.story.panels

import view.util.scalaQuestSwingComponents.SqSwingBoxPanel

import java.awt.Color
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
