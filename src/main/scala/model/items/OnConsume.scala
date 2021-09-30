package model.items

import model.characters.Character

sealed trait OnConsume extends (Character => Unit)

case class IncrementHealthOnConsume(value: Int) extends OnConsume {
  override def apply(character: Character): Unit =
    character.properties.health.currentPS = character.properties.health.currentPS + value
}

case class DecrementHealthOnConsume(value: Int) extends OnConsume {
  override def apply(character: Character): Unit =
    character.properties.health.currentPS = character.properties.health.currentPS - value
}