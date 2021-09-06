package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel
import model.characters.properties.stats.StatName
import model.characters.{Character, Enemy, Player}
import view.battle.BattleView

/**
 * Controller used to handle a full battle, taking the player input and handling the enemy actions.
 * Associated with [[view.battle.BattleView]].
 */
sealed trait BattleController extends SubController {
  /**
   * Attack action, based on the current character turn.
   */
  def attack(): Unit

  /**
   * Try to escape from the current battle.
   */
  def attemptEscape(): Unit

  /**
   * Inform that an item in inventory has been used.
   */
  def useItem(): Unit

  /**
   * Set the enemy turn.
   */
  def enemyTurn(): Unit

  /**
   * Switch control to inventory when asked by the player.
   */
  def goToInventory(): Unit

  /**
   * Switch control to story when the battle is over.
   */
  def goToStory(): Unit
}

object BattleController {

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): BattleController =
    new BattleControllerImpl(gameMasterController, storyModel)

  private class BattleControllerImpl(private val gameMasterController: GameMasterController,
                                     private val storyModel: StoryModel) extends BattleController {

    private val battleView: BattleView = BattleView(this)
    private val player: Player = storyModel.player
    private var roundNarrative: String = ""

    override def attack(): Unit = {
      roundNarrative = ""
      if (isPlayerFaster){
        playerAttack()
        enemyAttack()
      } else {
        roundNarrative += "The enemy is faster than you...\n"
        enemyAttack()
        playerAttack()
      }
      setOpponentsInfo()
      battleNarrative()
      battleView.render()
    }

    private def isPlayerFaster: Boolean = {
      val enemy: Enemy = storyModel.currentStoryNode.enemy.get
      storyModel.player.properties.stat(StatName.Dexterity).value > enemy.properties.stat(StatName.Dexterity).value
    }

    private def enemyAttack(): Unit = {
      val enemy: Option[Enemy] = storyModel.currentStoryNode.enemy
      var damageInflicted: Int = 0
      damageInflicted = damage(enemy.get, player)
      player.properties.health.currentPS -= damageInflicted
      roundNarrative +=  enemy.get.name + " inflicted " + damageInflicted + " damage to " + player.name + "!\n"
      checkBattleResult()
    }

    private def playerAttack(): Unit = {
      val enemy: Option[Enemy] = storyModel.currentStoryNode.enemy
      var damageInflicted: Int = 0
      damageInflicted = damage(player, enemy.get)
      enemy.get.properties.health.currentPS -= damageInflicted
      roundNarrative +=  player.name + " inflicted " + damageInflicted + " damage to " + enemy.get.name + "!\n"
      checkBattleResult()
    }

    override def enemyTurn(): Unit = {
      roundNarrative = ""
      enemyAttack()
      setOpponentsInfo()
    }

    private def battleNarrative(): Unit = {
      battleView.narrative(roundNarrative)
    }

    override def attemptEscape(): Unit = {
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

    override def execute(): Unit = {
      battleView.narrative("OH NO! There is a enemy. " +
        "You must battle vs " + storyModel.currentStoryNode.enemy.get.name + ".")
      setOpponentsInfo()
      battleView.render()
    }

    override def close(): Unit = gameMasterController.close()

    override def goToInventory(): Unit = gameMasterController.executeOperation(OperationType.InventoryOperation)

    override def goToStory(): Unit = gameMasterController.executeOperation(OperationType.StoryOperation)

    private def damage(attacker: Character, target: Character): Int = {
      val damageInflicted = (attacker.properties.stat(StatName.Strength).value +
        attacker.properties.stat(StatName.Dexterity).value) -
        target.properties.stat(StatName.Constitution).value
      if (damageInflicted > 0) damageInflicted else 1
    }

    private def checkBattleResult(): Unit = {
      val enemy: Enemy = storyModel.currentStoryNode.enemy.get
      if (storyModel.player.properties.health.currentPS == 0){
        battleView.battleResult(false)
      } else if (enemy.properties.health.currentPS == 0){
        battleView.battleResult()
      }
      battleView.narrative(roundNarrative)
      battleView.render()
    }

    private def setOpponentsInfo(): Unit = {
      battleView.setPlayerName(storyModel.player.name)
      battleView.setPlayerHealth((storyModel.player.properties.health.currentPS,
        storyModel.player.properties.health.maxPS))
      battleView.setEnemyName(storyModel.currentStoryNode.enemy.get.name)
      battleView.setEnemyHealth((storyModel.currentStoryNode.enemy.get.properties.health.currentPS,
        storyModel.currentStoryNode.enemy.get.properties.health.maxPS))
    }
  }
}
