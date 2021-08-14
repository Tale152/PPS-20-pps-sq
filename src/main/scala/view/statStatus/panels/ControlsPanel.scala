package view.statStatus.panels

import java.awt.event.ActionEvent
import javax.swing.{JButton, JPanel}

object ControlsPanel {

  /**
   * Panel contained in [[view.statStatus.StatStatusView]]; renders one button to go back.
   * @param onBack strategy to be applied on back button click
   */
  class ControlsPanel(private val onBack: Unit => Unit) extends JPanel{
    this.setOpaque(false)
    private val backButton: JButton = new JButton("Back")
    backButton.addActionListener((_: ActionEvent) => onBack())
    this.add(backButton)
  }

  def apply(onBack: Unit => Unit): ControlsPanel = new ControlsPanel(onBack)
}
