package controller.game.subcontroller

import controller.Controller

/**
 * A SubController is a sub-component of the GameMasterController.
 * SubControllers are coordinated by the GameMasterController using the
 * [[GameMasterController#executeOperation(controller.OperationType)]] method,
 * which delegates the job to the correct SubController specifying the corresponding OperationType.
 * @see [[controller.game.GameMasterController]]
 * @see [[controller.game.OperationType]]
 */
trait SubController extends Controller