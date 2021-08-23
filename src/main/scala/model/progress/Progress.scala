package model.progress

import model.characters.Player

trait Progress extends Serializable {
  def serializableHistory: SerializableHistory

  def player: Player
}

object Progress {
  private class ProgressImpl(override val serializableHistory: SerializableHistory, override val player: Player)
    extends Progress

  def apply(serializableHistory: SerializableHistory, player: Player): Progress =
    new ProgressImpl(serializableHistory, player)
}