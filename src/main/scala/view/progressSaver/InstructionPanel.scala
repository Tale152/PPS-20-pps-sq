package view.progressSaver

import view.util.scalaQuestSwingComponents.{SqFont, SqSwingGridPanel}
import java.awt.Color
import javax.swing.JTextArea

case class InstructionPanel() extends SqSwingGridPanel(0, 1) {
  private val instructionFontSize: Int = 25
  private val textArea = new JTextArea("Do you want to save your progress in this story?")
  textArea.setFont(SqFont(bold = true, instructionFontSize))
  textArea.setBackground(Color.BLACK)
  textArea.setForeground(Color.WHITE)
  textArea.setLineWrap(true)
  textArea.setWrapStyleWord(true)
  this.add(textArea)
}
