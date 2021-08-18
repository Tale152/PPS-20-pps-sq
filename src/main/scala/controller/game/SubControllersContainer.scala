package controller.game

import controller.game.subcontroller.{HistoryController, PlayerInfoController, StoryController}
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
}

object SubControllersContainer {

  private class SubControllersContainerImpl(gameMasterController: GameMasterController,
                                            storyModel: StoryModel) extends SubControllersContainer {

    private val _storyController: StoryController = StoryController(gameMasterController, storyModel)
    private val _statStatusController: PlayerInfoController = PlayerInfoController(gameMasterController, storyModel)
    private val _historyController: HistoryController = HistoryController(gameMasterController, storyModel)

    override def storyController: StoryController = _storyController

    override def statStatusController: PlayerInfoController = _statStatusController

    override def historyController: HistoryController = _historyController
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): SubControllersContainer =
    new SubControllersContainerImpl(gameMasterController, storyModel)
}
