package view.util.scalaQuestSwingComponents

import java.awt.Color

import javax.swing.JTextArea
import view.util.StringUtil.DefaultFontSize

/**
 * Represents a custom textArea for ScalaQuest.
 *
 * @param content  the text to display in the textarea.
 * @param editable true if the textArea needs to be editable.
 */
case class SqSwingTextArea(content: String, editable: Boolean = false) extends JTextArea(content) {
  this.setEditable(editable)
  this.setFocusable(false)
  this.setLineWrap(true)
  this.setWrapStyleWord(true)
  this.setBackground(Color.BLACK)
  this.setForeground(Color.WHITE)
  this.setFont(SqFont(bold = false, DefaultFontSize))
}
