package controller.game

import controller.game.subcontroller.{PlayerInfoController, StoryController}
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
}

object SubControllersContainer {

  private class SubControllersContainerImpl(gameMasterController: GameMasterController,
                                            storyModel: StoryModel) extends SubControllersContainer {

    private val _storyController: StoryController = StoryController(gameMasterController, storyModel)
    private val _statStatusController: PlayerInfoController = PlayerInfoController(gameMasterController, storyModel)

    override def storyController: StoryController = _storyController

    override def statStatusController: PlayerInfoController = _statStatusController
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): SubControllersContainer =
    new SubControllersContainerImpl(gameMasterController, storyModel)
}
