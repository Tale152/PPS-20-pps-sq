package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel
import model.characters.properties.stats.StatName
import model.characters.{Character, Enemy, Player}
import view.battle.BattleView

sealed trait BattleController extends SubController {
  def attack(): Unit
  def attemptEscape(): Unit
  def useItem(): Unit
  def enemyTurn(): Unit
  def goToInventory(): Unit
  def goToStory(): Unit
}

object BattleController {

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): BattleController =
    new BattleControllerImpl(gameMasterController, storyModel)

  private class BattleControllerImpl(private val gameMasterController: GameMasterController,
                                     private val storyModel: StoryModel) extends BattleController {

    private val battleView: BattleView = BattleView(this)
    private val player: Player = storyModel.player
    private var playerRound: Boolean = true;

    override def attack(): Unit = {
      val enemy: Option[Enemy] = storyModel.currentStoryNode.enemy

      println(damage(player, enemy.get))
      enemy.get.properties.health.currentPS -= damage(player, enemy.get)
      println(enemy.get.properties.health.currentPS)
      battleView.narrative("Player has " + player.properties.health.currentPS +
        " PS. Enemy has " + enemy.get.properties.health.currentPS + " PS.")
      battleView.render()
      checkBattleResult()
    }

    override def attemptEscape(): Unit =  {
      battleView.escapeResult(escapeCondition())
    }

    private def escapeCondition(): Boolean = {
      val enemy: Enemy = storyModel.currentStoryNode.enemy.get

      (storyModel.player.properties.stat(StatName.Dexterity).value +
        player.properties.stat(StatName.Intelligence).value) >
        enemy.properties.stat(StatName.Dexterity).value
    }

    override def useItem(): Unit = {
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
      playerRound = true
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

    override def goToInventory(): Unit = gameMasterController.executeOperation(OperationType.InventoryOperation)

    override def goToStory(): Unit = gameMasterController.executeOperation(OperationType.StoryOperation)

    private def damage(attacker: Character, target: Character): Int =
      (attacker.properties.stat(StatName.Strength).value + attacker.properties.stat(StatName.Dexterity).value) -
        target.properties.stat(StatName.Constitution).value

    private def checkBattleResult(): Unit = {
      val enemy: Enemy = storyModel.currentStoryNode.enemy.get
      if (storyModel.player.properties.health.currentPS == 0) {
        battleView.battleResult(false)
      } else if (enemy.properties.health.currentPS == 0){
        battleView.battleResult()
      } else {
        //next round
      }
      battleView.render()
    }

    override def enemyTurn(): Unit = playerRound = false
  }
}
