package model

import model.characters.Player
import model.characters.properties.stats.{Stat, StatName}
import model.nodes.{Pathway, StoryNode}
import specs.FlatTestSpec

class StoryModelTest extends FlatTestSpec {
  val maxPS: Int = 100
  val stats: Set[Stat] = Set(Stat(1, StatName.Speed))
  val mainPlayer: Player = Player("Jonathan", maxPS, stats)

  val nextStoryNode: StoryNode = StoryNode(1, "nextNarrative", Set.empty, List())
  val storyNode: StoryNode = StoryNode(0, "narrative", Set(Pathway("pathway", nextStoryNode, None)), List())

  val storyModel: StoryModel = StoryModel(mainPlayer, storyNode)

  "The story model" should "know who is the player" in {
    storyModel.player shouldEqual mainPlayer
  }

  it should "know which is the current story node" in {
    storyModel.currentStoryNode shouldEqual storyNode
  }

  it should "update history and current story node when appending to history" in {
    storyModel.appendToHistory(nextStoryNode)
    storyModel.history shouldEqual List(storyNode, nextStoryNode)
    storyModel.currentStoryNode shouldEqual nextStoryNode
  }

  it should "throw IllegalArgumentException if at least two StoryNode ID are equals" in {
    val endNodeA: StoryNode = StoryNode(0, "narrative", Set.empty, List())
    val pathwayMidToEndA: Pathway = Pathway("description", endNodeA, None)
    val endNodeB: StoryNode = StoryNode(2, "narrative", Set.empty, List())
    val pathwayMidToEndB: Pathway = Pathway("description", endNodeB, None)
    val midNode: StoryNode = StoryNode(1, "narrative", Set(pathwayMidToEndA, pathwayMidToEndB), List())
    val pathwayStartToMid: Pathway = Pathway("description", midNode, None)
    val startingNode: StoryNode = StoryNode(0, "narrative", Set(pathwayStartToMid), List())
    intercept[IllegalArgumentException] {
      StoryModel(mainPlayer, startingNode)
    }
  }

  it should "throw IllegalArgumentException if provided history is not valid while instantiating" in {
    val startingNode: StoryNode = StoryNode(0, "narrative", Set.empty, List())
    val unreachableNode: StoryNode = StoryNode(1, "narrative", Set.empty, List())
    intercept[IllegalArgumentException] {
      StoryModel(mainPlayer, List(startingNode, unreachableNode))
    }
  }

  it should "throw IllegalArgumentException if trying to append an unreachable node" in {
    val unreachableNode: StoryNode = StoryNode(2, "narrative", Set.empty, List())
    val reachableNode: StoryNode = StoryNode(1, "narrative", Set.empty, List())
    val startingNode: StoryNode = StoryNode(0, "narrative", Set(Pathway("pathway", reachableNode, None)), List())
    val sm: StoryModel = StoryModel(mainPlayer, startingNode)
    intercept[IllegalArgumentException] {
      sm.appendToHistory(unreachableNode)
    }
  }

}
