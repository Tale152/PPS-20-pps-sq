package view.util.common

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

/**
 * Panel contained in [[view.playerInfo.PlayerInfoView]] and [[view.battle.BattleView]].
 * Renders the character's name.
 *
 * @param name the name to render.
 */
case class CharacterNamePanel(role: String, name: String) extends SqSwingFlowPanel {
  private val fontSize = 30
  this.add(SqSwingLabel(role + ": " + name, labelSize = fontSize, bold = true))
}
