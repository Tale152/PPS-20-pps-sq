package controller

import controller.subcontroller.StoryController
import model.StoryModel

/**
 * Component that encapsulates all the [[controller.subcontroller.SubController]] used by the
 * [[controller.MasterController]].
 */
sealed trait SubControllersContainer {

  /**
   * @return the [[controller.subcontroller.StoryController]] instance in the current game.
   */
  def storyController: StoryController
}

object SubControllersContainer {

  class SubControllersContainerImpl(storyModel: StoryModel) extends SubControllersContainer {

    private val _storyController: StoryController = StoryController(storyModel)

    override def storyController: StoryController = _storyController
  }

  def apply(storyModel: StoryModel): SubControllersContainer = new SubControllersContainerImpl(storyModel)
}
