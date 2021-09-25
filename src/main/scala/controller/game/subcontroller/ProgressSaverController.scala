package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import controller.util.ResourceNames
import controller.util.serialization.DeserializerChecker.checkOnLoadingFile
import controller.util.serialization.ProgressSerializer
import model.{Progress, StoryModel}
import view.progressSaver.ProgressSaverView

/**
 * This [[controller.game.subcontroller.SubController]] contains the logic to save the progress for the current game.
 *
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
      checkOnLoadingFile(() => {
        ProgressSerializer.serializeProgress(
          Progress(storyModel.history.map(n => n.id), storyModel.player),
          ResourceNames.storyProgressPath(storyModel.storyName)()
        )
        progressSaverView.showSuccessFeedback(_ => gameMasterController.executeOperation(OperationType.StoryOperation))
      }, "Error on saving progress")
    }

    override def execute(): Unit = progressSaverView.render()

    override def close(): Unit = gameMasterController.executeOperation(OperationType.StoryOperation)
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): ProgressSaverController =
    new ProgressSaverControllerImpl(gameMasterController, storyModel)
}
