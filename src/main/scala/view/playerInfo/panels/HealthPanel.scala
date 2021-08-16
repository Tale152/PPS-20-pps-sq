package view.playerInfo.panels

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

object HealthPanel {

  /**
   * Panel contained in [[view.playerInfo.PlayerInfoView]]; renders the player's health.
 *
   * @param health a pair containing current health and max health
   */
  class HealthPanel(health: (Int, Int)) extends SqSwingFlowPanel {
    this.add(SqSwingLabel("Health: " + health._1 + "/" + health._2))
  }

  def apply(health: (Int, Int)): HealthPanel = new HealthPanel(health)
}
