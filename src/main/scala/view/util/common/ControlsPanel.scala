package view.util.common

import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingGridPanel}

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.{AbstractAction, JComponent, KeyStroke}

/**
 * Panel containing one or more buttons with associated actions and key listeners.
 *
 * @param controls list of pairs in which the first argument is the key associated to the listener, the second is a
 *                 pair containing the string to print on the button and the action on click / key pressed
 */
case class ControlsPanel(controls: List[(String, (String, ActionListener))]) extends SqSwingGridPanel(0, 2) {
  if (controls.map(a => a._1).toSet.size != controls.size) throw new IllegalArgumentException()
  for (a <- controls) {
    val btn = SqSwingButton(a._2._1, a._2._2)
    this.add(btn)
    this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
      .put(KeyStroke.getKeyStroke("control " + a._1.toUpperCase), a._1.toUpperCase)
    this.getActionMap.put(a._1.toUpperCase, new AbstractAction() {
      override def actionPerformed(e: ActionEvent): Unit = btn.doClick()
    })
  }
}

object StandardKeyListener {
  def quitKeyListener(dialogMessage: String, actionListener: ActionListener): (String, (String, ActionListener)) = {
    ("q", ("[Q] Quit", _ => SqYesNoSwingDialog("Exit Confirm", dialogMessage, actionListener)))
  }
}