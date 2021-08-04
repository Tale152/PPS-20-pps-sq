package model.characters

/**
 * Trait that represents a Character.
 */
sealed trait Character {
  val name: String
}

/**
 * Case class that represents a Player, the main and only character of a story.
 *
 * @param name the name of the player.
 */
case class Player(override val name: String) extends Character {
  require(name != "") // may throw IllegalArgumentException
}
