package controller.game.subcontroller

import controller.game.GameMasterController
import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.{Stat, StatName}
import model.nodes.{Pathway, StoryNode}
import org.scalatest.BeforeAndAfterEach
import specs.FlatTestSpec

class StoryControllerTest extends FlatTestSpec with BeforeAndAfterEach {

  val maxPS: Int = 100
  val stats: Set[Stat] = Set(Stat(1, StatName.Speed))
  val player: Player = Player("player", maxPS, stats)
  val destinationNode: StoryNode = StoryNode(1, "narrative", None, Set.empty, List())
  val pathway: Pathway = Pathway("description", destinationNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(pathway), List())

  var storyModel: StoryModel = StoryModel(player, startingNode)
  var gameMasterController: GameMasterController = GameMasterController(storyModel)
  var storyController: StoryController = StoryController(gameMasterController, storyModel)

  override def beforeEach(): Unit = {
    super.beforeEach()
    storyModel = StoryModel(player, startingNode)
    gameMasterController = GameMasterController(storyModel)
    storyController = StoryController(gameMasterController, storyModel)
  }


  "Choosing a Pathway that does not belong to current node" should "throw IllegalArgumentException" in {
    val incorrectNode: StoryNode = StoryNode(3, "narrative", None, Set.empty, List())
    val incorrectPathway: Pathway = Pathway("description", incorrectNode, None)
    intercept[IllegalArgumentException] {
      storyController.choosePathWay(incorrectPathway)
    }
  }

}
