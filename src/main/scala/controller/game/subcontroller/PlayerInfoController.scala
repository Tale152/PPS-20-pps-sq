package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel
import model.characters.properties.stats.StatName.StatName
import view.playerInfo.PlayerInfoView

/**
 * The [[controller.game.subcontroller.SubController]] that contains the logic to
 * set the stats of the [[model.characters.Player]].
 */
sealed trait PlayerInfoController extends SubController

object PlayerInfoController {

  private class PlayerInfoControllerImpl(private val gameMasterController: GameMasterController,
                                 private val storyModel: StoryModel) extends PlayerInfoController {

    private val playerInfoView: PlayerInfoView = PlayerInfoView(this)

    override def execute(): Unit = {
      playerInfoView.setStats(getStatStructure)
      playerInfoView.setPlayerName(storyModel.player.name)
      playerInfoView.setHealth(
        (storyModel.player.properties.health.currentPS, storyModel.player.properties.health.maxPS)
      )
      playerInfoView.render()
    }

    override def close(): Unit = gameMasterController.executeOperation(OperationType.StoryOperation)

    /**
     * @return A list containing tuples structured as:
     *         (The [[model.characters.properties.stats.StatName.StatName]],
     *         (the original stat value, the current stat value))
     */
    private def getStatStructure: List[(StatName, (Int, Int))] = {
      val currentStats =
        for(original <- storyModel.player.properties.stats)
          yield storyModel.player.properties.modifiedStat(original.statName)
      val statViewStructure =
        for(original <- storyModel.player.properties.stats)
          yield (
            original.statName,
            (original.value, currentStats.filter(s => s.statName == original.statName).last.value)
          )
      statViewStructure.toList
    }
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): PlayerInfoController =
    new PlayerInfoControllerImpl(gameMasterController, storyModel)
}
