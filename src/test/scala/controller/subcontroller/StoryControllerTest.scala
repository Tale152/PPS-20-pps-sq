package controller.subcontroller

import model.{StoryModel, StoryModelImpl}
import model.characters.Player
import model.nodes.{Pathway, StoryNode}
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

class StoryControllerTest extends FlatSpec with Matchers with BeforeAndAfterEach {

  val player: Player = Player("player")
  val destinationNode: StoryNode = StoryNode(1, "narrative", Set.empty)
  val alwaysTrueCondition: StoryModel => Boolean = _ => true
  val pathway: Pathway = Pathway("description", destinationNode, alwaysTrueCondition)
  val startingNode: StoryNode = StoryNode(0, "narrative", Set(pathway))

  var storyModel: StoryModel = StoryModelImpl(player, startingNode)
  var storyController: StoryController = StoryController(storyModel)

  override def beforeEach(): Unit = {
    super.beforeEach()
    storyModel = StoryModelImpl(player, startingNode)
    storyController = StoryController(storyModel)
  }


  "Choosing a Pathway that does not belong to current node" should "throw IllegalArgumentException" in {
    val incorrectNode: StoryNode = StoryNode(3, "narrative", Set.empty)
    val incorrectPathway: Pathway = Pathway("description", incorrectNode, alwaysTrueCondition)
    intercept[IllegalArgumentException] {
      storyController.choosePathWay(incorrectPathway)
    }
  }

  //TODO: adapt to bypass user input
  //"Choosing a Pathway" should "lead to the correct StoryNode" in {
  // storyController.choosePathWay(pathway)
  //  storyModel.currentStoryNode shouldEqual destinationNode
  //}

}
