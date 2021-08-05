package controller

import controller.OperationType.{OperationType}

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
