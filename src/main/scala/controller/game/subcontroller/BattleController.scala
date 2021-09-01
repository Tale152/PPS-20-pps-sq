package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel
import model.characters.properties.stats.StatName
import model.characters.{Character, Enemy, Player}
import model.items.Item

sealed trait BattleController extends SubController {
  def attack(attacker: Character, target: Character): Unit
  def attemptEscape(): Unit
  def useItem(user: Character, item: Item, target: Character): Unit
  def goToInventory(): Unit
}

object BattleController {

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): BattleController =
    new BattleControllerImpl(gameMasterController, storyModel)

  private class BattleControllerImpl(private val gameMasterController: GameMasterController,
                                     private val storyModel: StoryModel) extends BattleController {
    require(storyModel.currentStoryNode.enemy.isDefined)

    val enemy: Enemy = storyModel.currentStoryNode.enemy.get
    val player: Player = storyModel.player

    override def attack(attacker: Character, target: Character): Unit =
      target.properties.health.currentPS = target.properties.health.currentPS - damage(attacker, target)

    override def attemptEscape(): Unit =  {
      if((player.properties.stat(StatName.Speed).value +
        player.properties.stat(StatName.Intelligence).value) > enemy.properties.stat(StatName.Speed).value) {
        //battle won
      } else {
        //next round
      }
    }

    override def useItem(user: Character, item: Item, target: Character): Unit = {
      //Set in view what happened with the data returned by the inventory controller
      checkBattleResult()
    }

    /**
     * Start the Controller.
     */
    override def execute(): Unit = {
      if (enemy.properties.stat(StatName.Speed).value >= player.properties.stat(StatName.Speed).value){
        //todo the enemy attacks first (set method to establish what the enemy will do)
      } else {
        //todo let the player decide what to do
      }
    }

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = gameMasterController.close()

    override def goToInventory(): Unit = ??? //gameMasterController.executeOperation(OperationType.InventoryController)

    private def damage(attacker: Character, target: Character): Int =
      (attacker.properties.stat(StatName.Strength).value * attacker.properties.stat(StatName.Dexterity).value) -
        (target.properties.stat(StatName.Defence).value * target.properties.stat(StatName.Speed).value)

    private def checkBattleResult(): Unit = {
      if (player.properties.health.currentPS == 0) {
        //the battle is lost
      } else if (enemy.properties.health.currentPS == 0){
        //the battle is won
      } else {
        //next round

      }
    }
  }
}