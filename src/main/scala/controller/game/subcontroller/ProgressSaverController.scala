package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel


/**
 * This [[controller.game.subcontroller.SubController]] contains the logic to save the progress for the current game.
 * @see [[view.progressSaver.ProgressSaverView]]
 */
sealed trait ProgressSaverController extends SubController {
  /**
   * Saves the progress for current game.
   */
  def saveProgress(): Unit
}

object ProgressSaverController {

  private class ProgressSaverControllerImpl(private val gameMasterController: GameMasterController,
                                            private val storyModel: StoryModel) extends ProgressSaverController {
    override def saveProgress(): Unit = ???

    override def execute(): Unit = ???

    override def close(): Unit = gameMasterController.executeOperation(OperationType.StoryOperation)
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): ProgressSaverController =
    new ProgressSaverControllerImpl(gameMasterController, storyModel)

}
