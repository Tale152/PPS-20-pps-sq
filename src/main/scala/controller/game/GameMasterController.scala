package controller.game

import controller.{ApplicationController, Controller}
import controller.game.OperationType.OperationType
import model.StoryModel

/**
 * The Game Master Controller is the main Controller regarding a game associated to a story.
 * It acts as a coordinator, using the [[GameMasterController#executeOperation(controller.OperationType)]] method
 * to specify the receiver of the delegate job.
 * The Game Master Controller is strongly related to one or more [[controller.game.subcontroller.SubController]].
 */
sealed trait GameMasterController extends Controller {

  /**
   * Delegate the job to a sub-component.
   *
   * @param operation the OperationType.
   */
  def executeOperation(operation: OperationType): Unit
}

object GameMasterController {

  private class GameMasterControllerImpl(private val storyModel: StoryModel) extends GameMasterController {

    private val subControllersContainer: SubControllersContainer = SubControllersContainer(this, storyModel)

    /**
     * Start the Controller.
     */
    override def execute(): Unit = executeOperation(OperationType.StoryOperation)

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = ApplicationController.execute()

    override def executeOperation(op: OperationType): Unit = op match {
      case OperationType.StoryOperation => subControllersContainer.storyController.execute()
      case OperationType.PlayerInfoOperation => subControllersContainer.statStatusController.execute()
      case OperationType.HistoryOperation => subControllersContainer.historyController.execute()
      case OperationType.ProgressSaverOperation => subControllersContainer.progressSaverController.execute()
      case OperationType.BattleOperation => subControllersContainer.battleController.execute()
    }
  }

  def apply(storyModel: StoryModel): GameMasterController = new GameMasterControllerImpl(storyModel)
}
