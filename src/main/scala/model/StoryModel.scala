package model

import model.characters.Player

/**
 * Class that represents the StoryModel, the class that gives the controller all data about the model state.
 * @param player the player instance that it's playing the game.
 */
class StoryModel(val player: Player) {
}

/**
 * Companion object of the StoryModel class.
 */
object StoryModel {
  def apply(player: Player): StoryModel = new StoryModel(player)
}


