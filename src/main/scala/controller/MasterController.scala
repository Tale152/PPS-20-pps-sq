package controller

import controller.OperationType.{OperationType, StoryOperation}
import controller.util.ResourceName
import model.characters.Player
import model.nodes.StoryNode
import model.StoryModelImpl
import model.nodes.util.StoryNodeSerializer.deserializeStory

/**
 * The Master Controller is the Main Controller of the application.
 * It acts as a coordinator, using the [[controller.MasterController#executeOperation(controller.OperationType)]] method
 * to specify the receiver of the delegate job.
 * The MasterController is strongly related to one or more [[controller.subcontroller.SubController]].
 */
sealed trait MasterController extends Controller {

  /**
   * Delegate the job to a sub-component
   * @param operation the OperationType
   */
  def executeOperation(operation: OperationType): Unit

  /**
   * @param storyURI the string that represent the URI where the story file is.
   * @return The first [[model.nodes.StoryNode]] of the story chosen.
   */
  def loadStory(storyURI: String): StoryNode
}

object MasterController extends MasterController {
  private var subControllersContainer: Option[SubControllersContainer] = None

  /**
   * Start the Controller.
   */
  override def execute(): Unit = {
    val deserializedStoryNode: StoryNode = loadStory(ResourceName.storyDirectoryPath() + "/random-story.ser")
    subControllersContainer = Some(SubControllersContainer(StoryModelImpl(Player("Player"), deserializedStoryNode)))
    executeOperation(StoryOperation)
  }

  /**
   * Defines the actions to do when the Controller execution is over.
   */
  override def close(): Unit = System.exit(0)

  override def executeOperation(op: OperationType): Unit = op match {
    case StoryOperation => subControllersContainer.get.storyController.execute()
  }

  override def loadStory(storyURI: String) : StoryNode = deserializeStory(storyURI)
}
