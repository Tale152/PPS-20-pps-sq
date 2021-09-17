package controller.editor

import controller.Controller
import model.nodes.StoryNode
import view.editor.InfoView

/**
 * The Info Controller is used to get information about a certain story, starting from its route node.
 */
trait InfoController extends Controller {

}

object InfoController {

  private class InfoControllerImpl(private val previousController: Controller,
                                   private val routeNode: StoryNode) extends InfoController {

    private val infoView: InfoView = InfoView(this)

    /**
     * Start the Controller.
     */
    override def execute(): Unit = infoView.render()

    /**
     * Defines the actions to do when the Controller execution is over.
     */
    override def close(): Unit = previousController.execute()

  }

  def apply(previousController: Controller, routeNode: StoryNode): InfoController =
    new InfoControllerImpl(previousController, routeNode)

}