package controller.game.subcontroller

import controller.Controller
import controller.game.GameMasterController

/**
 * A SubController is a sub-component of the MasterController.
 * SubControllers are coordinated by the MasterController using the
 * [[GameMasterController#executeOperation(controller.OperationType)]] method,
 * which delegates the job to the correct SubController specifying the corresponding [[controller.OperationType]].
 */
trait SubController extends Controller