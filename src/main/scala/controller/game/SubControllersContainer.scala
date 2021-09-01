package controller.game

import controller.game.subcontroller._
import model.StoryModel

/**
 * Component that encapsulates all the [[controller.game.subcontroller.SubController]] used by the
 * [[GameMasterController]].
 */
sealed trait SubControllersContainer {

  /**
   * @return the [[controller.game.subcontroller.StoryController]] instance in the current game.
   */
  def storyController: StoryController

  /**
   * @return the [[controller.game.subcontroller.PlayerInfoController]] instance in the current game.
   */
  def statStatusController: PlayerInfoController

  /**
   * @return the [[controller.game.subcontroller.HistoryController]] instance in the current game.
   */
  def historyController: HistoryController

  /**
   * @return the [[controller.game.subcontroller.ProgressSaverController]] instance in the current game.
   */
  def progressSaverController: ProgressSaverController

  /**
   * @return the [[controller.game.subcontroller.BattleController]] instance in the current game.
   */
  def battleController: BattleController
}

object SubControllersContainer {

  private class SubControllersContainerImpl(gameMasterController: GameMasterController,
                                            storyModel: StoryModel) extends SubControllersContainer {

    private val story: StoryController = StoryController(gameMasterController, storyModel)
    private val statStatus: PlayerInfoController = PlayerInfoController(gameMasterController, storyModel)
    private val history: HistoryController = HistoryController(gameMasterController, storyModel)
    private val progressSaver: ProgressSaverController = ProgressSaverController(gameMasterController, storyModel)
    private val battle: BattleController = BattleController(gameMasterController, storyModel)

    override def storyController: StoryController = story

    override def statStatusController: PlayerInfoController = statStatus

    override def historyController: HistoryController = history

    override def progressSaverController: ProgressSaverController = progressSaver

    override def battleController: BattleController = battle
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): SubControllersContainer =
    new SubControllersContainerImpl(gameMasterController, storyModel)
}
