package view.util.characterInfo

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

import java.awt.Color

object CharacterHealthPanel {

  /**
   * Panel contained in [[view.playerInfo.PlayerInfoView]] and [[view.battle.BattleView]].
   * Renders the character's health.
   *
   * @param health a pair containing current health and max health.
   */
  class CharacterHealthPanel(health: (Int, Int)) extends SqSwingFlowPanel {
    private val fontSize = 25
    this.add(SqSwingLabel("Health: " + health._1, if (health._1 == health._2) {
      Color.GREEN
    } else {
      Color.RED
    }, fontSize, bold = true))
    this.add(SqSwingLabel("/" + health._2, Color.GREEN, fontSize, bold = true))
  }

  def apply(health: (Int, Int)): CharacterHealthPanel = new CharacterHealthPanel(health)
}
