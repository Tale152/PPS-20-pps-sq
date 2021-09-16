package view.playerConfiguration.panels

import view.util.scalaQuestSwingComponents.{SqSwingGridPanel, SqSwingLabel}

import javax.swing.JTextField

/**
 * Panel contained in [[view.playerConfiguration.PlayerConfigurationView]]; renders instructions for the player
 */
case class PlayerNamePanel() extends SqSwingGridPanel(1, 2) {
  private val textSize = 17
  this.add(SqSwingLabel("Choose your alias: ", bold = true, labelSize = textSize))
  private val nameTextField = new JTextField()
  this.add(nameTextField)

  def playerName: String = nameTextField.getText

}