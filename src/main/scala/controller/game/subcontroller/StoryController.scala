package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel
import model.nodes.Pathway
import view.story.StoryView

/**
 * The [[controller.game.subcontroller.SubController]] that contains the logic to update the
 * [[model.StoryModel]] current [[model.nodes.StoryNode]].
 */
sealed trait StoryController extends SubController {

  /**
   * Choose the pathway to update the [[model.StoryModel]] current [[model.nodes.StoryNode]].
   *
   * @param pathway the chosen pathway.
   * @throws IllegalArgumentException when selecting a pathway that does not belong to the current
   *                                  [[model.nodes.StoryNode]]
   */
  def choosePathWay(pathway: Pathway): Unit

  /**
   * Calls the [[controller.game.GameMasterController]] to grant control to the
   * [[controller.game.subcontroller.PlayerInfoController]].
   */
  def goToStatStatus(): Unit

  /**
   * Calls the [[controller.game.GameMasterController]] to grant control to the
   * [[controller.game.subcontroller.HistoryController]].
   */
  def goToHistory(): Unit

  /**
   * Calls the [[controller.game.GameMasterController]] to grant control to the
   * [[controller.game.subcontroller.ProgressSaverController]].
   */
  def goToProgressSaver(): Unit
}

object StoryController {

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): StoryController =
    new StoryControllerImpl(gameMasterController, storyModel)

  class StoryControllerImpl(private val gameMasterController: GameMasterController, private val storyModel: StoryModel)
    extends StoryController {

    private val storyView: StoryView = StoryView(this)

    override def execute(): Unit = {

        storyView.setNarrative(storyModel.currentStoryNode.narrative)
        storyView.setPathways(
          storyModel.currentStoryNode.pathways.filter(
            p => p.prerequisite.isEmpty || (p.prerequisite.nonEmpty && p.prerequisite.get(storyModel)))
        )
        storyView.render()

    }

    override def close(): Unit = gameMasterController.close()

    override def choosePathWay(pathway: Pathway): Unit = {
      if (!storyModel.currentStoryNode.pathways.contains(pathway)) {
        throw new IllegalArgumentException(
          "The selected Pathway does not belong to the current StoryNode: " + pathway.toString
        )
      }
      storyModel.appendToHistory(pathway.destinationNode)
      this.execute()
    }

    override def goToStatStatus(): Unit = gameMasterController.executeOperation(OperationType.PlayerInfoOperation)

    override def goToHistory(): Unit = gameMasterController.executeOperation(OperationType.HistoryOperation)

    override def goToProgressSaver(): Unit = gameMasterController.executeOperation(OperationType.ProgressSaverOperation)

    private def goToBattle(): Unit = gameMasterController.executeOperation(OperationType.BattleOperation)
  }

}
