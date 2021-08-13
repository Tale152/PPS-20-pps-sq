package view.statConfiguration.panels

import java.awt.event.ActionEvent
import javax.swing.{JButton, JPanel}

object ButtonsPanel {

  /**
   * Panel contained in [[view.statConfiguration.StatConfigurationView]]; renders two button:
   * one to confirm and one to go back
   * @param onBack strategy to be applied on back button click
   * @param onConfirm strategy to be applied on confirm button click
   */
  class ButtonsPanel(private val onBack: Unit => Unit, private val onConfirm: Unit => Unit) extends JPanel{
    this.setOpaque(false)
    private val backButton: JButton = new JButton("Back")
    backButton.addActionListener((_: ActionEvent) => onBack())
    private val confirmButton: JButton = new JButton("Confirm")
    confirmButton.addActionListener((_: ActionEvent) => onConfirm())
    this.add(backButton)
    this.add(confirmButton)
  }

  def apply(onBack: Unit => Unit, onContinue: Unit => Unit): ButtonsPanel = new ButtonsPanel(onBack, onContinue)

}
