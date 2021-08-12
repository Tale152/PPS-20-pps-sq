package controller.game

import controller.{ApplicationController, Controller}
import controller.game.OperationType.{OperationType, StoryOperation}
import model.StoryModel

/**
 * The Master Controller is the Main Controller of the application.
 * It acts as a coordinator, using the [[GameMasterController#executeOperation(controller.OperationType)]] method
 * to specify the receiver of the delegate job.
 * The MasterController is strongly related to one or more [[controller.game.subcontroller.SubController]].
 */
sealed trait GameMasterController extends Controller {

  /**
   * Delegate the job to a sub-component
   *
   * @param operation the OperationType
   */
  def executeOperation(operation: OperationType): Unit
}

object GameMasterController {

  private class GameMasterControllerImpl(private val storyModel: StoryModel) extends GameMasterController {

    private val subControllersContainer: SubControllersContainer = SubControllersContainer(this, storyModel)

    /**
     * Start the Controller.
     */
    override def execute(): Unit = executeOperation(StoryOperation)

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = ApplicationController.execute()

    override def executeOperation(op: OperationType): Unit = op match {
      case StoryOperation => subControllersContainer.storyController.execute()
    }
  }

  def apply(storyModel: StoryModel): GameMasterController = new GameMasterControllerImpl(storyModel)
}
