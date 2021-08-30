package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel
import model.characters.properties.stats.Stat
import model.characters.properties.stats.StatName.StatName
import view.playerInfo.PlayerInfoView

sealed trait PlayerInfoController extends SubController

object PlayerInfoController {

  private class PlayerInfoControllerImpl(private val gameMasterController: GameMasterController,
                                 private val storyModel: StoryModel) extends PlayerInfoController {

    private val playerInfoView: PlayerInfoView = PlayerInfoView(this)

    override def execute(): Unit = {
      playerInfoView.setStats(getStatStructure)
      playerInfoView.setPlayerName(storyModel.player.name)
      playerInfoView.setHealth(
        (storyModel.player.properties.health.currentPS, storyModel.player.properties.health.currentPS)
      )
      playerInfoView.render()
    }

    override def close(): Unit = gameMasterController.executeOperation(OperationType.StoryOperation)

    private def getStatStructure: List[(StatName, (Int, Int))] = {
      val currentStats =
        for(original <- storyModel.player.properties.stats)
          yield storyModel.player.properties
            .statModifiers(original.statName)
            .foldLeft(original)((o, m) => Stat(m.modifyStrategy(o.value), o.statName))
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
