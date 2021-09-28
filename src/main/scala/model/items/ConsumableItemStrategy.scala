package model.items

import model.characters.Character

sealed trait ConsumableItemStrategy extends (Character => Unit)

case class IncrementHealthStrategy(value: Int) extends ConsumableItemStrategy {
  override def apply(character: Character): Unit =
    character.properties.health.currentPS = character.properties.health.currentPS + value
}

case class DecrementHealthStrategy(value: Int) extends ConsumableItemStrategy {
  override def apply(character: Character): Unit =
    character.properties.health.currentPS = character.properties.health.currentPS - value
}