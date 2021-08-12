package controller

import controller.game.GameMasterController
import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.StatName.StatName
import model.characters.properties.stats.{Stat, StatName}
import model.nodes.StoryNode
import view.statConfiguration.StatConfigurationView

trait StatConfigurationController extends Controller {

  def setStatValue(stat: StatName, value: Int): Unit

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
