package model.items

import model.characters.Character

/**
 * Trait that represents the strategy to apply on a [[model.items.ConsumableItem]].
 */
sealed trait OnConsume extends (Character => Unit)

/**
 * Increment the [[model.characters.Character]] current PS by some value.
 * @param value the value to add to the current PS of the character.
 */
case class IncrementHealthOnConsume(private val value: Int) extends OnConsume {
  override def apply(character: Character): Unit =
    character.properties.health.currentPS = character.properties.health.currentPS + value
}

/**
 * Decrement the [[model.characters.Character]] current PS by some value.
 * @param value the value to subtract from the current PS of the character.
 */
case class DecrementHealthOnConsume(private val value: Int) extends OnConsume {
  override def apply(character: Character): Unit =
    character.properties.health.currentPS = character.properties.health.currentPS - value
}