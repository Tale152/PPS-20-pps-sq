package view.playerConfiguration.panels

import view.util.scalaQuestSwingComponents.{SqSwingGridPanel, SqSwingLabel}
import javax.swing.JTextField
import view.util.StringUtil.DefaultFontSize

/**
 * Panel contained in [[view.playerConfiguration.PlayerConfigurationView]]; renders instructions for the player
 */
case class PlayerNamePanel() extends SqSwingGridPanel(1, 2) {
  this.add(SqSwingLabel("Choose your alias: ", bold = true, labelSize = DefaultFontSize))
  private val nameTextField = new JTextField()
  this.add(nameTextField)

  def playerName: String = nameTextField.getText

}