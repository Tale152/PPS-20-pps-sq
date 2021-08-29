package view.progressSaver

import view.util.scalaQuestSwingComponents.{SqSwingBorderPanel, SqSwingFlowPanel, SqSwingLabel}

import java.awt.{Color, Component}
import javax.swing.{Box, BoxLayout}

case class InstructionPanel() extends SqSwingBorderPanel {
  private val InstructionFontSize: Int = 25
  private val box = new Box(BoxLayout.Y_AXIS)
  box.setAlignmentY(Component.CENTER_ALIGNMENT)
  box.add(Box.createVerticalGlue())
  box.add(new SqSwingFlowPanel {
    this.add(SqSwingLabel("Do you want to save your progress in this story?", Color.WHITE, InstructionFontSize))
  })
  box.add(Box.createVerticalGlue())
  this.add(box)
}
