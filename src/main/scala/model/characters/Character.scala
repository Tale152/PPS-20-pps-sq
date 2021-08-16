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
 * Case class that represents a Player, the main and only character of a story.
 *
 * @param name  the name of the player.
 * @param maxPS the maximum number of PS with full life
 * @param stats a set of predefined stats of the player
 */
case class Player(override val name: String, maxPS: Int, private val stats: Set[Stat]) extends Character {
  require(name != "" && maxPS > 0 && stats.nonEmpty)
  override val properties: PropertiesContainer = PropertiesContainer(maxPS, stats)
}
