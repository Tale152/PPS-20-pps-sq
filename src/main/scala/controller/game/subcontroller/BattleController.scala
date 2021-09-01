package controller.game.subcontroller

import controller.game.GameMasterController
import model.StoryModel
import model.characters.properties.stats.StatName
import model.characters.{Character, Enemy, Player}
import model.items.Item
import view.battle.BattleView

sealed trait BattleController extends SubController {
  def attack(): Unit
  def attemptEscape(): Unit
  def useItem(user: Character, item: Item, target: Character): Unit
  def goToInventory(): Unit
}

object BattleController {

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): BattleController =
    new BattleControllerImpl(gameMasterController, storyModel)

  private class BattleControllerImpl(private val gameMasterController: GameMasterController,
                                     private val storyModel: StoryModel) extends BattleController {

    private val battleView: BattleView = BattleView(this)
    private val player: Player = storyModel.player

    override def attack(): Unit = {
      val enemy: Option[Enemy] = storyModel.currentStoryNode.enemy

      println("ATTACK " + enemy.isDefined)

      println(damage(player, enemy.get))
      enemy.get.properties.health.currentPS -= damage(player, enemy.get)
      println(enemy.get.properties.health.currentPS)
      battleView.narrative("Player has " + player.properties.health.currentPS +
        " PS. Enemy has " + enemy.get.properties.health.currentPS + " PS.")
      battleView.render()
      checkBattleResult()
    }

    override def attemptEscape(): Unit =  {
      val enemy: Option[Enemy] = storyModel.currentStoryNode.enemy
      val player: Player = storyModel.player
      println("Attempt escape")
      if((player.properties.stat(StatName.Speed).value +
        player.properties.stat(StatName.Intelligence).value) > enemy.get.properties.stat(StatName.Speed).value) {
        //battle won
        battleView.narrative("Escaped successfully")
      } else {
        //next round
        battleView.narrative("Escape failed")
      }
      battleView.render()
    }

    override def useItem(user: Character, item: Item, target: Character): Unit = {
      //Set in view what happened with the data returned by the inventory controller
      checkBattleResult()
    }

    /**
     * Start the Controller.
     */
    override def execute(): Unit = {

      battleView.narrative(storyModel.currentStoryNode.narrative)
      //set player and enemy health

      battleView.render()

      /*if (enemy.properties.stat(StatName.Speed).value >= player.properties.stat(StatName.Speed).value){
        //todo the enemy attacks first (set method to establish what the enemy will do)
      } else {
        //todo let the player decide what to do
      }*/
    }

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = gameMasterController.close()

    override def goToInventory(): Unit = ??? //gameMasterController.executeOperation(OperationType.InventoryController)

    private def damage(attacker: Character, target: Character): Int =
      (attacker.properties.stat(StatName.Strength).value + attacker.properties.stat(StatName.Dexterity).value) -
        target.properties.stat(StatName.Defence).value

    private def checkBattleResult(): Unit = {
      val enemy: Option[Enemy] = storyModel.currentStoryNode.enemy
      val player: Player = storyModel.player
      if (player.properties.health.currentPS == 0) {
        //the battle is lost
        battleView.narrative("Battle lost")
      } else if (enemy.get.properties.health.currentPS == 0){
        //the battle is won
        battleView.narrative("Battle won")
      } else {
        //next round
      }
      battleView.render()
    }
  }
}
