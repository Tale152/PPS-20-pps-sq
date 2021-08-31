package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import controller.util.ResourceName
import controller.util.serialization.ProgressSerializer
import model.StoryModel
import model.progress.{Progress, SerializableHistory}
import view.progressSaver.ProgressSaverView


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

    private val progressSaverView: ProgressSaverView = ProgressSaverView(this)

    override def saveProgress(): Unit = {
      try{
        ProgressSerializer.serializeProgress(
          Progress(SerializableHistory(storyModel.history.map(n => n.id)), storyModel.player),
          ResourceName.storyProgressPath(ResourceName.testRandomStoryName)
        )
        progressSaverView.showSuccessFeedback(_ => gameMasterController.executeOperation(OperationType.StoryOperation))
      } catch {
        case e: Exception =>
          println(e)
          progressSaverView.showFailureFeedback(
            _ => gameMasterController.executeOperation(OperationType.StoryOperation)
          )
      }
    }

    override def execute(): Unit = progressSaverView.render()

    override def close(): Unit = gameMasterController.executeOperation(OperationType.StoryOperation)
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): ProgressSaverController =
    new ProgressSaverControllerImpl(gameMasterController, storyModel)

}
