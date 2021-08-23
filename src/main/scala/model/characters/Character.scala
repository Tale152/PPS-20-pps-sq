package model.characters

import model.characters.properties.PropertiesContainer
import model.characters.properties.stats.Stat

/**
 * Trait that represents a Character.
 */
sealed trait Character {
  val name: String

  val properties: PropertiesContainer
}

/**
 * Abstraact class that represents an AbstractCharacter.
 *
 * @param name  the name of the Character.
 * @param maxPS the maximum number of PS with full life
 * @param stats a set of predefined stats of the Character
 */
abstract class AbstractCharacter(override val name: String, maxPS: Int, private val stats: Set[Stat])
  extends Character {
  require(name != "" && maxPS > 0 && stats.nonEmpty)
  override val properties: PropertiesContainer = PropertiesContainer(maxPS, stats)
}

/**
 *
 * Case class that represents the one and only protagonist of the game.
 *
 * @param name  the name of the Player.
 * @param maxPS the maximum number of PS with full life
 * @param stats a set of predefined stats of the Player
 */
case class Player(override val name: String, maxPS: Int, private val stats: Set[Stat])
  extends AbstractCharacter(name, maxPS, stats)

/**
 * Case class that represents every enemy of the game.
 *
 * @param name  the name of the Enemy.
 * @param maxPS the maximum number of PS with full life
 * @param stats a set of predefined stats of the Enemy
 */
case class Enemy(override val name: String, maxPS: Int, private val stats: Set[Stat])
  extends AbstractCharacter(name, maxPS, stats)