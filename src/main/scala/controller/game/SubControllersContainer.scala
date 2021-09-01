package controller.game

import controller.game.subcontroller.{HistoryController, InventoryController,
  PlayerInfoController, ProgressSaverController, StoryController}
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
   * @return the [[controller.game.subcontroller.InventoryController]] instance in the current game.
   */
  def inventoryController: InventoryController
}

object SubControllersContainer {

  private class SubControllersContainerImpl(gameMasterController: GameMasterController,
                                            storyModel: StoryModel) extends SubControllersContainer {

    private val story: StoryController = StoryController(gameMasterController, storyModel)
    private val statStatus: PlayerInfoController = PlayerInfoController(gameMasterController, storyModel)
    private val history: HistoryController = HistoryController(gameMasterController, storyModel)
    private val progressSaver: ProgressSaverController = ProgressSaverController(gameMasterController, storyModel)
    private val inventory: InventoryController = InventoryController(gameMasterController, storyModel)

    override def storyController: StoryController = story

    override def statStatusController: PlayerInfoController = statStatus

    override def historyController: HistoryController = history

    override def progressSaverController: ProgressSaverController = progressSaver

    override def inventoryController: InventoryController = inventory
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): SubControllersContainer =
    new SubControllersContainerImpl(gameMasterController, storyModel)
}
