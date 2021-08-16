package view.playerInfo.panels

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

object PlayerNamePanel {

  /**
   * Panel contained in [[view.playerInfo.PlayerInfoView]]; renders the player's name.
 *
   * @param name the name to render
   */
  class PlayerNamePanel(name: String) extends SqSwingFlowPanel {
    this.add(SqSwingLabel("Player: " + name))
  }

  def apply(name: String): PlayerNamePanel = new PlayerNamePanel(name)
}
