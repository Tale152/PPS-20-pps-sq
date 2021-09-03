package controller.game.subcontroller

import controller.game.GameMasterController
import mock.MockFactory.mockStoryModel
import model.StoryModel
import model.nodes.{Pathway, StoryNode}
import specs.FlatTestSpec

class StoryControllerTest extends FlatTestSpec {

  val playerMaxPS : Int = 100
  val storyModel: StoryModel = mockStoryModel(playerMaxPS)
  var gameMasterController: GameMasterController = GameMasterController(storyModel)
  var storyController: StoryController = StoryController(gameMasterController, storyModel)

  "Choosing a Pathway that does not belong to current node" should "throw IllegalArgumentException" in {
    val incorrectNode: StoryNode = StoryNode(3, "narrative", None, Set.empty, List())
    val incorrectPathway: Pathway = Pathway("description", incorrectNode, None)
    intercept[IllegalArgumentException] {
      storyController.choosePathWay(incorrectPathway)
    }
  }

}
