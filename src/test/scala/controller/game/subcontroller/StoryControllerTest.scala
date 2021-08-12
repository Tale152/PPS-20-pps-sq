package controller.game.subcontroller

import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.{Stat, StatName}
import model.nodes.{Pathway, StoryNode}
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

class StoryControllerTest extends FlatSpec with Matchers with BeforeAndAfterEach {

  val maxPS: Int = 100
  val stats: Set[Stat] = Set(Stat(1, StatName.Speed))
  val player: Player = Player("player", maxPS, stats)
  val destinationNode: StoryNode = StoryNode(1, "narrative", Set.empty)
  val pathway: Pathway = Pathway("description", destinationNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", Set(pathway))

  var storyModel: StoryModel = StoryModel(player, startingNode)
  var storyController: StoryController = StoryController(null, storyModel)

  override def beforeEach(): Unit = {
    super.beforeEach()
    storyModel = StoryModel(player, startingNode)
    storyController = StoryController(null, storyModel)
  }


  "Choosing a Pathway that does not belong to current node" should "throw IllegalArgumentException" in {
    val incorrectNode: StoryNode = StoryNode(3, "narrative", Set.empty)
    val incorrectPathway: Pathway = Pathway("description", incorrectNode, None)
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
