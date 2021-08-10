package controller.subcontroller

import controller.MasterController
import model.StoryModel
import model.nodes.Pathway
import view.StoryView
import view.StoryView.StoryView

/**
 * The [[controller.subcontroller.SubController]] that contains the logic to update the
 * [[model.StoryModel]] current [[model.nodes.StoryNode]].
 */
sealed trait StoryController extends SubController {

  /**
   * Choose the pathway to update the [[model.StoryModel]] current [[model.nodes.StoryNode]].
   *
   * @param pathway the chosen pathway
   * @throws IllegalArgumentException when selecting a pathway that does not belong to the current
   *                                  [[model.nodes.StoryNode]]
   */
  def choosePathWay(pathway: Pathway): Unit
}

object StoryController {

  class StoryControllerImpl(private val storyModel: StoryModel) extends StoryController {

    private val storyView: StoryView = StoryView(this, () => scala.io.StdIn.readLine())

    /**
     * Start the Controller.
     */
    override def execute(): Unit = {
      storyView.setNarrative(storyModel.currentStoryNode.narrative)
      if (storyModel.currentStoryNode.pathways.isEmpty) {
        storyView.setPathways(Set())
      } else {
        storyView.setPathways(
          storyModel.currentStoryNode.pathways.filter(
            p => p.prerequisite.isEmpty || (p.prerequisite.nonEmpty && p.prerequisite.get(storyModel))
          )
        )
      }
      storyView.render()
    }

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = MasterController.close()

    override def choosePathWay(pathway: Pathway): Unit = {
      if (!storyModel.currentStoryNode.pathways.contains(pathway)) {
        throw new IllegalArgumentException(
          "The selected Pathway does not belong to the current StoryNode: " + pathway.toString
        )
      }
      storyModel.currentStoryNode = pathway.destinationNode
      this.execute()
    }
  }

  def apply(storyModel: StoryModel): StoryController = new StoryControllerImpl(storyModel)

}
