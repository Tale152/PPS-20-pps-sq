package view.util.common

import java.awt.Color

import view.util.StringUtil.TitleSize
import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

/**
 * Panel contained in [[view.playerInfo.PlayerInfoView]] and [[view.battle.BattleView]].
 * Renders the character's health.
 *
 * @param health a pair containing current health and max health.
 */
case class CharacterHealthPanel(health: (Int, Int)) extends SqSwingFlowPanel {
  this.add(SqSwingLabel("Health: ", Color.GREEN, TitleSize, bold = true))
  this.add(SqSwingLabel(health._1.toString, if (health._1 == health._2) {
    Color.GREEN
  } else {
    Color.RED
  }, TitleSize, bold = true))
  this.add(SqSwingLabel("/" + health._2, Color.GREEN, TitleSize, bold = true))
}
