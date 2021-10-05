package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import controller.util.audio.MusicPlayer
import model.StoryModel
import model.characters.properties.stats.StatName
import model.characters.{Character, Enemy, Player}
import model.items.Item
import view.battle.BattleView

import scala.util.Random

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

    private val failAttackChance: Int = 10
    private val escapeChance: Int = 20

    private val battleView: BattleView = BattleView(this)
    private val player: Player = storyModel.player
    private var battleFirstRound = true
    private var roundNarrative: String = ""
    private var playerInventory: List[Item] = List()

    override def execute(): Unit = {
      if (battleFirstRound) {
        battleFirstRound = false
        playerInventory = storyModel.player.inventory
        battleView.narrative("OH NO! There is an enemy. " +
          "You must battle vs " + storyModel.currentStoryNode.enemy.get.name + ".")
      } else if (isInventoryChanged) {
        playerInventory = storyModel.player.inventory
        usedItem()
      }
      setOpponentsInfo()
      battleView.render()
    }

    private def isInventoryChanged: Boolean = {
      !playerInventory.forall(storyModel.player.inventory.contains)
    }

    private def usedItem(): Unit = {
      roundNarrative = "You used an item.\n"
      checkBattleResult()
      enemyAttack()
    }

    private def isPlayerFaster: Boolean = {
      val enemy: Enemy = storyModel.currentStoryNode.enemy.get
      storyModel.player.properties.modifiedStat(StatName.Dexterity).value >
        enemy.properties.modifiedStat(StatName.Dexterity).value
    }

    override def attack(): Unit = {
      if (isPlayerFaster) {
        roundNarrative = "You are faster than your enemy!\n"
        playerAttack()
        enemyAttack()
      } else {
        roundNarrative = "The enemy is faster than you...\n"
        enemyAttack()
        playerAttack()
      }
    }

    private def enemyAttack(): Unit = {
      if (!isBattleOver) {
        executeAttack(storyModel.currentStoryNode.enemy.get, player)
      }
    }

    private def playerAttack(): Unit = {
      if (!isBattleOver) {
        executeAttack(player, storyModel.currentStoryNode.enemy.get)
      }
    }

    private def executeAttack(attacker: Character, target: Character): Unit = {
      if(failedAttack(attacker, target)){
        roundNarrative += attacker.name + " attack failed!\n"
      } else {
        val damageInflicted: Int = damage(attacker, target)
        target.properties.health.currentPS -= damageInflicted
        roundNarrative += attacker.name + " inflicted " + damageInflicted + " damage to " + target.name + "!\n"
      }
      checkBattleResult()
    }

    //used to randomly generate a number to establish if an attack is successful or not (also base on character stats)
    private def failedAttack(attacker: Character, target: Character): Boolean = {
      val attackProbabilityBaseValue: Int =
        (attacker.properties.stat(StatName.Wisdom).value -
          target.properties.stat(StatName.Intelligence).value) +
          failAttackChance

      Random.nextInt(
        if (attackProbabilityBaseValue > 0) attackProbabilityBaseValue else 1
      ) == 0
    }

    private def damage(attacker: Character, target: Character): Int = {
      val damageInflicted: Int = attacker.properties.modifiedStat(StatName.Strength).value -
        target.properties.modifiedStat(StatName.Constitution).value
      val actualDamage: Int = if (damageInflicted > 0) damageInflicted else 1

      actualDamage + Random.nextInt(
        (attacker.properties.stat(StatName.Dexterity).value
          + attacker.properties.stat(StatName.Constitution).value) / 2)
    }

    private def checkBattleResult(): Unit = {
      setOpponentsInfo()
      battleView.narrative(roundNarrative)
      battleView.render()
      val enemy: Enemy = storyModel.currentStoryNode.enemy.get
      if (storyModel.player.properties.health.currentPS == 0) {
        battleView.battleResult(false)
      } else if (enemy.properties.health.currentPS == 0) {
        battleFirstRound = true
        battleView.battleResult()
      }
    }

    private def isBattleOver: Boolean = {
      storyModel.player.properties.health.currentPS == 0 ||
        storyModel.currentStoryNode.enemy.get.properties.health.currentPS == 0
    }

    private def setOpponentsInfo(): Unit = {
      battleView.setPlayerName(storyModel.player.name)
      battleView.setPlayerHealth((storyModel.player.properties.health.currentPS,
        storyModel.player.properties.health.maxPS))
      battleView.setEnemyName(storyModel.currentStoryNode.enemy.get.name)
      battleView.setEnemyHealth((storyModel.currentStoryNode.enemy.get.properties.health.currentPS,
        storyModel.currentStoryNode.enemy.get.properties.health.maxPS))
    }

    override def attemptEscape(): Unit = {
      if (escapeCondition) {
        battleFirstRound = true
        storyModel.currentStoryNode.enemy.get.properties.health.currentPS = 0
        battleView.escaped()
      } else {
        escapeFailed()
      }
    }

    private def escapeCondition: Boolean = {
      val enemy: Enemy = storyModel.currentStoryNode.enemy.get
      Random.nextInt(escapeChance - (storyModel.player.properties.modifiedStat(StatName.Intelligence).value -
        enemy.properties.modifiedStat(StatName.Dexterity).value)) == 0
    }

    private def escapeFailed(): Unit = {
      roundNarrative = "You tried to escape, but couldn't. Now it's enemy turn.\n"
      enemyAttack()
    }

    override def close(): Unit = {
      MusicPlayer.playMenuMusic()
      gameMasterController.close()
    }

    override def goToInventory(): Unit = gameMasterController.executeOperation(OperationType.InventoryOperation)

    override def goToStory(): Unit = {
      MusicPlayer.playStoryMusic()
      gameMasterController.executeOperation(OperationType.StoryOperation)
    }
  }
}
