package view.util.characterInfo

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

import java.awt.Color

/**
 * Panel contained in [[view.playerInfo.PlayerInfoView]] and [[view.battle.BattleView]].
 * Renders the character's health.
 *
 * @param health a pair containing current health and max health.
 */
case class CharacterHealthPanel(health: (Int, Int)) extends SqSwingFlowPanel {
  private val fontSize = 25
  this.add(SqSwingLabel("Health: ", Color.GREEN, fontSize, bold = true))
  this.add(SqSwingLabel(health._1.toString, if (health._1 == health._2) {
    Color.GREEN
  } else {
    Color.RED
  }, fontSize, bold = true))
  this.add(SqSwingLabel("/" + health._2, Color.GREEN, fontSize, bold = true))
}
