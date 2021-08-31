package model.progress

import model.characters.Player

/**
 * Represents the progress in a story. Contains everything that needs to be saved to resume a game.
 * Used to save the progress or load previous progress.
 * @see [[model.progress.SerializableHistory]]
 * @see [[model.characters.Player]]
 */
trait Progress extends Serializable {
  def serializableHistory: SerializableHistory
  def player: Player
}

object Progress {
  private class ProgressImpl(override val serializableHistory: SerializableHistory, override val player: Player)
    extends Progress{
    require(serializableHistory != null && player != null)
  }

  def apply(serializableHistory: SerializableHistory, player: Player): Progress =
    new ProgressImpl(serializableHistory, player)
}
