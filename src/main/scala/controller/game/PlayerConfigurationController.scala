package controller.game

import controller.{ApplicationController, Controller}
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
sealed trait PlayerConfigurationController extends Controller {

  /**
   * Used by the PlayerConfigurationView when the user modifies the value of a stat.
   *
   * @param stat  the stat that has been modified.
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
  def confirm(playerName: String): Unit
}

object PlayerConfigurationController {

  private object PlayerConfigValues {
    val PlayerMaxPs: Int = 100
    val InitialStatValue: Int = 5
    val InitialRemainingPointsValue: Int = 15
    val StatSortingFunction: (Stat, Stat) => Boolean = (s1, s2) => s1.toString > s2.toString
  }

  import controller.game.PlayerConfigurationController.PlayerConfigValues._

  class PlayerConfigurationControllerImpl(private val storyName: String, private val startingNode: StoryNode)
    extends PlayerConfigurationController {

    private val playerConfigurationView: PlayerConfigurationView = PlayerConfigurationView(this)
    private var _stats: List[Stat] = List(
      Stat(InitialStatValue, StatName.Wisdom),
      Stat(InitialStatValue, StatName.Dexterity),
      Stat(InitialStatValue, StatName.Strength),
      Stat(InitialStatValue, StatName.Charisma),
      Stat(InitialStatValue, StatName.Intelligence),
      Stat(InitialStatValue, StatName.Constitution)
    ).sortWith(StatSortingFunction)
    private var _remainingPoints: Int = InitialRemainingPointsValue

    private def updateView(): Unit = {
      playerConfigurationView.setStats(_stats)
      playerConfigurationView.setRemainingPoints(_remainingPoints)
      playerConfigurationView.render()
    }

    override def execute(): Unit = updateView()

    override def close(): Unit = ApplicationController.execute()

    override def setStatValue(stat: StatName, value: Int): Unit = {
      _stats = (_stats.filter(s => s.statName != stat) :+ Stat(value, stat)).sortWith(StatSortingFunction)
      _remainingPoints =
        InitialStatValue * _stats.size + InitialRemainingPointsValue - _stats.foldLeft[Int](0)((v, s) => v + s.value)
      updateView()
    }

    override def confirm(playerName: String): Unit =
      GameMasterController(StoryModel(storyName, Player(playerName, PlayerMaxPs, _stats.toSet), startingNode)).execute()

  }

  def apply(storyName: String, startingNode: StoryNode): PlayerConfigurationController =
    new PlayerConfigurationControllerImpl(storyName, startingNode)
}
