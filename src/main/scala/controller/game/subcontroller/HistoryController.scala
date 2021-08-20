package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel
import view.history.HistoryView

/**
 * Controller used to control the history of a StoryModel.
 * Coupled with a [[view.history.HistoryView]]
 * @see [[model.StoryModel]]
 */
sealed trait HistoryController extends SubController

object HistoryController {

  private class HistoryControllerImpl(private val gameMasterController: GameMasterController,
                                      private val storyModel: StoryModel) extends HistoryController {

    private val historyView: HistoryView = HistoryView(this)

    override def execute(): Unit = {
      if(storyModel.history.size > 1){
        historyView.setPreviousChoices(
          storyModel
            .history
            .sliding(2)
            .map[(String, String)](
              l => (l.head.narrative, l.head.pathways.find(p => p.destinationNode == l(1)).get.description)
            ).toList
        )
      } else {
        historyView.setPreviousChoices(List())
      }
      historyView.setCurrentNodeNarrative(storyModel.currentStoryNode.narrative)
      historyView.render()
    }

    override def close(): Unit = gameMasterController.executeOperation(OperationType.StoryOperation)
  }

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): HistoryController =
    new HistoryControllerImpl(gameMasterController, storyModel)
}
