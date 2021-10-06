package model

import model.characters.Player

/**
 * Represents the progress in a story. Contains everything that needs to be saved to resume a game.
 * Used to save the progress or load previous progress.
 *
 * @see [[model.characters.Player]]
 */
sealed trait Progress extends Serializable {

  /**
   * @return a List containing the ids of the StoryNodes that the player has visited while playing
   *         (ordered by visit order)
   */
  def serializableHistory: List[Int]

  /**
   * @return the [[Player]] instance to save
   */
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
        Option(player).nonEmpty
        && Option(serializableHistory).nonEmpty
        && serializableHistory.nonEmpty
        && serializableHistory.size == serializableHistory.toSet.size
    )
  }

  def apply(serializableHistory: List[Int], player: Player): Progress = new ProgressImpl(serializableHistory, player)
}
