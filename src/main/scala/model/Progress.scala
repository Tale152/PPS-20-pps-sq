package model

import model.characters.Player

/**
 * Represents the progress in a story. Contains everything that needs to be saved to resume a game.
 * Used to save the progress or load previous progress.
 *
 * @see [[model.characters.Player]]
 */
sealed trait Progress extends Serializable {
  def serializableHistory: List[Int]

  def player: Player
}

/**
 * Used to serialize the history.
 * @see [[model.StoryModel]]
 * @see [[model.nodes.StoryNode]]
 */
object Progress {
  private class ProgressImpl(override val serializableHistory: List[Int], override val player: Player)
    extends Progress {
    require(
      player != null
        && serializableHistory != null
        && serializableHistory.nonEmpty
        && serializableHistory.size == serializableHistory.toSet.size
    )
  }

  def apply(serializableHistory: List[Int], player: Player): Progress = new ProgressImpl(serializableHistory, player)
}
