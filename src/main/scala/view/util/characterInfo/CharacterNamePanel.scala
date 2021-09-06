package view.util.characterInfo

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

object CharacterNamePanel {

  /**
   * Panel contained in [[view.playerInfo.PlayerInfoView]] and [[view.battle.BattleView]].
   * Renders the character's name.
   *
   * @param name the name to render.
   */
  class CharacterNamePanel(name: String) extends SqSwingFlowPanel {
    private val fontSize = 30
    this.add(SqSwingLabel("Player: " + name, labelSize = fontSize, bold = true))
  }

  def apply(name: String): CharacterNamePanel = new CharacterNamePanel(name)
}
