package controller.game.subcontroller

import controller.game.GameMasterController
import model.StoryModel
import model.characters.Character
import model.items.Item

sealed trait BattleController extends SubController {
  def attack(target: Character): Unit
  def attemptEscape(): Unit
  def useItem(item: Item, target: Character): Unit
  def goToInventory(): Unit
}

object BattleController {

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): BattleController =
    new BattleControllerImpl(gameMasterController, storyModel)

  private class BattleControllerImpl(private val gameMasterController: GameMasterController,
                                     private val storyModel: StoryModel) extends BattleController {

    override def attack(target: Character): Unit = ???

    override def attemptEscape(): Unit = ???

    override def useItem(item: Item, target: Character): Unit = ???

    /**
     * Start the Controller.
     */
    override def execute(): Unit = ???

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = gameMasterController.close()

    override def goToInventory(): Unit = ???
  }
}