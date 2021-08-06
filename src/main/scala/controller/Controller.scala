package controller

/**
 * A controller that can start and stop its execution.
 */
trait Controller {

  /**
   * Start the Controller.
   */
  def execute(): Unit

  /**
   * Defines the actions to do when the Controller execution is over.
   */
  def close(): Unit

}
