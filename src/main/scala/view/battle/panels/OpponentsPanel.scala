package view.battle.panels

import java.awt.Color

import javax.swing.{BorderFactory, BoxLayout}
import javax.swing.border.TitledBorder
import view.util.scalaQuestSwingComponents.SqSwingBoxPanel

/**
 * A view component used in [[view.battle.BattleView]] to show the current status of the player and the enemy.
 * @param playerName is the player name.
 * @param playerHealth is a tuple of the current player health and the max health.
 * @param enemyName is the enemy name.
 * @param enemyHealth is a tuple of the current enemy health and the max health.
 */
case class OpponentsPanel(playerName: String, playerHealth: (Int, Int), enemyName: String, enemyHealth: (Int, Int))
  extends SqSwingBoxPanel(BoxLayout.Y_AXIS) {
  val border: TitledBorder = BorderFactory.createTitledBorder(
    BorderFactory.createLineBorder(Color.GRAY), "Battle opponents"
  )
  border.setTitleColor(Color.WHITE)
  this.setBorder(border)
  this.add(CharacterPanel("Player", playerName, playerHealth))
  this.add(SeparatorPanel())
  this.add(CharacterPanel("Enemy", enemyName, enemyHealth))
}

