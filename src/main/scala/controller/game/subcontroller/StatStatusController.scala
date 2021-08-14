package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel
import model.characters.properties.stats.Stat
import model.characters.properties.stats.StatName.StatName
import view.statStatus.StatStatusView

sealed trait StatStatusController extends SubController

object StatStatusController {

  class StatStatusControllerImpl(private val gameMasterController: GameMasterController,
                                 private val storyModel: StoryModel) extends StatStatusController {

    private val statStatusView: StatStatusView = StatStatusView(this)

    /**
     * Start the Controller.
     */
    override def execute(): Unit = {
      statStatusView.setStats(getStatStructure)
      statStatusView.setPlayerName(storyModel.player.name)
      statStatusView.setHealth(
        (storyModel.player.properties.health.currentPS, storyModel.player.properties.health.currentPS)
      )
      statStatusView.render()
    }

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = gameMasterController.executeOperation(OperationType.StoryOperation)

    private def getStatStructure: List[(StatName, (Int, Int))] = {
      val currentStats =
        for(original <- storyModel.player.properties.stats)
          yield storyModel.player.properties
            .statModifiers(original.statName())
            .foldLeft(original)((o, m) => Stat(m.modifyStrategy(o.value()), o.statName()))
      val statViewStructure =
        for(original <- storyModel.player.properties.stats)
          yield (
            original.statName(),
            (original.value(), currentStats.filter(s => s.statName() == original.statName()).last.value())
          )
      statViewStructure.toList
    }
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): StatStatusController =
    new StatStatusControllerImpl(gameMasterController, storyModel)
}
