package controller

import controller.game.GameMasterController
import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.StatName.StatName
import model.characters.properties.stats.{Stat, StatName}
import model.nodes.StoryNode
import view.statConfiguration.StatConfigurationView

/**
 * A controller that coordinates the process of user setting his stats; receives control from the ApplicationController
 * and grants it to the GameMasterController (or back to the ApplicationController if user selects back).
 * Coupled with a StatConfigurationView
 * @see [[view.statConfiguration.StatConfigurationView]]
 * @see [[GameMasterController]]
 * @see [[ApplicationController]]
 * @see [[model.characters.properties.stats.Stat]]
 */
trait StatConfigurationController extends Controller {

  /**
   * Used by the StatConfigurationView when the user modifies the value of a stat.
   * @param stat the stat that has been modified
   * @param value the new stat value
   * @see [[model.characters.properties.stats.Stat]]
   * @see [[view.statConfiguration.StatConfigurationView]]
   */
  def setStatValue(stat: StatName, value: Int): Unit

  /**
   * Used by the StatConfigurationView when the user has finished modifying his stats.
   * @see [[model.characters.properties.stats.Stat]]
   * @see [[view.statConfiguration.StatConfigurationView]]
   */
  def confirm(): Unit
}

object StatConfigurationController {

  private val PlayerMaxPs: Int = 100
  private val InitialStatValue: Int = 5
  private val InitialRemainingPointsValue: Int = 15
  private val statSorting: (Stat, Stat) => Boolean = (s1, s2) => s1.toString > s2.toString

  class StatConfigurationControllerImpl(private val startingNode: StoryNode) extends StatConfigurationController {

    private val statConfigurationView: StatConfigurationView = StatConfigurationView(this)
    private var _stats: List[Stat] = List(
      Stat(InitialStatValue, StatName.Wisdom),
      Stat(InitialStatValue, StatName.Dexterity),
      Stat(InitialStatValue, StatName.Strength),
      Stat(InitialStatValue, StatName.Charisma),
      Stat(InitialStatValue, StatName.Intelligence),
      Stat(InitialStatValue, StatName.Constitution)
    ).sortWith(statSorting)
    private var _remainingPoints: Int = InitialRemainingPointsValue

    private def updateView(): Unit = {
      statConfigurationView.setStats(_stats)
      statConfigurationView.setRemainingPoints(_remainingPoints)
      statConfigurationView.render()
    }

    /**
     * Start the Controller.
     */
    override def execute(): Unit = updateView()

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = ApplicationController.execute()

    override def setStatValue(stat: StatName, value: Int): Unit = {
      _stats = (_stats.filter(s => s.statName() != stat) :+ Stat(value, stat)).sortWith(statSorting)
      _remainingPoints =
        InitialStatValue * _stats.size + InitialRemainingPointsValue - _stats.foldLeft[Int](0)((v, s) => v + s.value())
      updateView()
    }

    override def confirm(): Unit =
      GameMasterController(StoryModel(Player("player", PlayerMaxPs, _stats.toSet), startingNode)).execute()

  }

  def apply(startingNode: StoryNode): StatConfigurationController = new StatConfigurationControllerImpl(startingNode)
}
