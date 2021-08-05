package controller

import controller.OperationType.{OperationType, StoryOperation}
import util.StoryModelUtil

/**
 * The Master Controller is the Main Controller of the application.
 * It acts as a coordinator, using the [[controller.MasterController#executeOperation(controller.OperationType)]] method
 * to specify the receiver of the delegate job.
 * The MasterController is strongly related to one or more [[controller.subcontroller.SubController]].
 */
sealed trait MasterController extends Controller {

  /**
   * Delegate the job to a sub-component
   * @param operation the OperationType
   */
  def executeOperation(operation: OperationType): Unit
}

object MasterController extends MasterController {

  private val subControllersContainer: Option[SubControllersContainer] = Some(
    SubControllersContainer(StoryModelUtil.storyModel)
  )

  override def executeOperation(op: OperationType): Unit = op match {
    case StoryOperation => subControllersContainer.get.storyController.execute()
  }

  /**
   * Start the Controller.
   */
  override def execute(): Unit = executeOperation(StoryOperation)

  /**
   * Defines the actions to do when the Controller execution is over.
   */
  override def close(): Unit = System.exit(0)

}
