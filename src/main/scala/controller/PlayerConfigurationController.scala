package controller

import controller.game.GameMasterController
import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.StatName.StatName
import model.characters.properties.stats.{Stat, StatName}
import model.nodes.StoryNode
import view.playerConfiguration.PlayerConfigurationView

/**
 * A controller that coordinates the process of user setting his stats; receives control from the ApplicationController
 * and grants it to the GameMasterController (or back to the ApplicationController if user selects back).
 * Coupled with a PlayerConfigurationView.
 *
 * @see [[view.playerConfiguration.PlayerConfigurationView]]
 * @see [[GameMasterController]]
 * @see [[ApplicationController]]
 * @see [[model.characters.properties.stats.Stat]]
 */
trait PlayerConfigurationController extends Controller {

  /**
   * Used by the PlayerConfigurationView when the user modifies the value of a stat.
   *
   * @param stat the stat that has been modified.
   * @param value the new stat value.
   * @see [[model.characters.properties.stats.Stat]]
   * @see [[view.playerConfiguration.PlayerConfigurationView]]
   */
  def setStatValue(stat: StatName, value: Int): Unit

  /**
   * Used by the PlayerConfigurationView when the user has finished modifying his stats.
   *
   * @see [[model.characters.properties.stats.Stat]]
   * @see [[view.playerConfiguration.PlayerConfigurationView]]
   */
  def confirm(): Unit
}

object PlayerConfigurationController {

  private object PlayerConfigValues {
    val playerMaxPs: Int = 100
    val initialStatValue: Int = 5
    val initialRemainingPointsValue: Int = 15
    val statSortingFunction: (Stat, Stat) => Boolean = (s1, s2) => s1.toString > s2.toString
  }

  import controller.PlayerConfigurationController.PlayerConfigValues.
  {initialRemainingPointsValue, initialStatValue, playerMaxPs, statSortingFunction}

  class PlayerConfigurationControllerImpl(private val startingNode: StoryNode) extends PlayerConfigurationController {

    private val statConfigurationView: PlayerConfigurationView = PlayerConfigurationView(this)
    private var _stats: List[Stat] = List(
      Stat(initialStatValue, StatName.Wisdom),
      Stat(initialStatValue, StatName.Dexterity),
      Stat(initialStatValue, StatName.Strength),
      Stat(initialStatValue, StatName.Charisma),
      Stat(initialStatValue, StatName.Intelligence),
      Stat(initialStatValue, StatName.Constitution)
    ).sortWith(statSortingFunction)
    private var _remainingPoints: Int = initialRemainingPointsValue

    private def updateView(): Unit = {
      statConfigurationView.setStats(_stats)
      statConfigurationView.setRemainingPoints(_remainingPoints)
      statConfigurationView.render()
    }

    override def execute(): Unit = updateView()

    override def close(): Unit = ApplicationController.execute()

    override def setStatValue(stat: StatName, value: Int): Unit = {
      _stats = (_stats.filter(s => s.statName != stat) :+ Stat(value, stat)).sortWith(statSortingFunction)
      _remainingPoints =
        initialStatValue * _stats.size + initialRemainingPointsValue - _stats.foldLeft[Int](0)((v, s) => v + s.value)
      updateView()
    }

    override def confirm(): Unit =
      GameMasterController(StoryModel(Player("player", playerMaxPs, _stats.toSet), startingNode)).execute()

  }

  def apply(startingNode: StoryNode): PlayerConfigurationController =
    new PlayerConfigurationControllerImpl(startingNode)
}
