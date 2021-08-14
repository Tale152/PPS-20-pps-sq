package controller.game

import controller.game.subcontroller.{StatStatusController, StoryController}
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
   * @return the [[controller.game.subcontroller.StatStatusController]] instance in the current game.
   */
  def statStatusController: StatStatusController
}

object SubControllersContainer {

  private class SubControllersContainerImpl(gameMasterController: GameMasterController,
                                            storyModel: StoryModel) extends SubControllersContainer {

    private val _storyController: StoryController = StoryController(gameMasterController, storyModel)
    private val _statStatusController: StatStatusController = StatStatusController(gameMasterController, storyModel)

    override def storyController: StoryController = _storyController

    override def statStatusController: StatStatusController = _statStatusController
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): SubControllersContainer =
    new SubControllersContainerImpl(gameMasterController, storyModel)
}