package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel

/**
 * Controller used to control the history of a StoryModel.
 * Coupled with a [[view.history.HistoryView]]
 * @see [[model.StoryModel]]
 */
sealed trait HistoryController extends SubController

object HistoryController {

  private class HistoryControllerImpl(private val gameMasterController: GameMasterController,
                                      private val storyModel: StoryModel) extends HistoryController {

    override def execute(): Unit = ???

    override def close(): Unit = gameMasterController.executeOperation(OperationType.StoryOperation)
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): HistoryController =
    new HistoryControllerImpl(gameMasterController, storyModel)
}
