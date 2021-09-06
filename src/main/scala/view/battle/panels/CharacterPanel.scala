package view.battle.panels

import javax.swing.BoxLayout
import view.util.common.{CharacterHealthPanel, CharacterNamePanel}
import view.util.scalaQuestSwingComponents.SqSwingBoxPanel

/**
 * Panel that shows the name and the health of a character.
 * @param role is a string used to define a player or an enemy.
 * @param name is the name of the character.
 * @param health is a tuple of the current health and the max health of the character.
 */
case class CharacterPanel(role: String, name: String, health: (Int, Int)) extends SqSwingBoxPanel(BoxLayout.Y_AXIS) {
  this.add(CharacterNamePanel(role, name))
  this.add(CharacterHealthPanel(health))
}
