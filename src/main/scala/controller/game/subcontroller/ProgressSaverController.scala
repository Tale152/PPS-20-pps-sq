package controller.game.subcontroller

import controller.game.GameMasterController
import model.StoryModel

sealed trait ProgressSaverController extends SubController {
  def saveProgress(): Unit
}

object ProgressSaverController {

  private class ProgressSaverControllerImpl(private val gameMasterController: GameMasterController,
                                            private val storyModel: StoryModel) extends ProgressSaverController {
    override def saveProgress(): Unit = ???

    /**
     * Start the Controller.
     */
    override def execute(): Unit = ???

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = ???
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): ProgressSaverController =
    new ProgressSaverControllerImpl(gameMasterController, storyModel)

}
