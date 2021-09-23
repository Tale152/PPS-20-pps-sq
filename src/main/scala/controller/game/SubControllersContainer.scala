package controller.game

import controller.game.subcontroller.{HistoryController, InventoryController,
  PlayerInfoController, ProgressSaverController, StoryController}
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
  val storyController: StoryController

  /**
   * @return the [[controller.game.subcontroller.PlayerInfoController]] instance in the current game.
   */
  val statStatusController: PlayerInfoController

  /**
   * @return the [[controller.game.subcontroller.HistoryController]] instance in the current game.
   */
  val historyController: HistoryController

  /**
   * @return the [[controller.game.subcontroller.ProgressSaverController]] instance in the current game.
   */
  val progressSaverController: ProgressSaverController

  /**
   * @return the [[controller.game.subcontroller.InventoryController]] instance in the current game.
   */
  val inventoryController: InventoryController

  /**
   * @return the [[controller.game.subcontroller.BattleController]] instance in the current game.
   */
  val battleController: BattleController

}

object SubControllersContainer {

  private class SubControllersContainerImpl(gameMasterController: GameMasterController,
                                            storyModel: StoryModel) extends SubControllersContainer {

    private val story: StoryController = StoryController(gameMasterController, storyModel)
    private val statStatus: PlayerInfoController = PlayerInfoController(gameMasterController, storyModel)
    private val history: HistoryController = HistoryController(gameMasterController, storyModel)
    private val progressSaver: ProgressSaverController = ProgressSaverController(gameMasterController, storyModel)
    private val inventory: InventoryController = InventoryController(gameMasterController, storyModel)
    private val battle: BattleController = BattleController(gameMasterController, storyModel)

    override val storyController: StoryController = story

    override val statStatusController: PlayerInfoController = statStatus

    override val historyController: HistoryController = history

    override val progressSaverController: ProgressSaverController = progressSaver

    override val inventoryController: InventoryController = inventory

    override val battleController: BattleController = battle

  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): SubControllersContainer =
    new SubControllersContainerImpl(gameMasterController, storyModel)
}
