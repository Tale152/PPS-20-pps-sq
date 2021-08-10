package util

import model.StoryModel
import model.characters.Player
import model.nodes.{Pathway, StoryNode}

object StoryModelUtil {

  private val player: Player = Player("player")
  private val finalStoryNode4: StoryNode = StoryNode(4, "final-4", Set.empty)
  private val finalStoryNode5: StoryNode = StoryNode(5, "final-5", Set.empty)
  private val finalStoryNode6: StoryNode = StoryNode(6, "final-6", Set.empty)

  private val pathway1to4: Pathway = Pathway("go to 4", finalStoryNode4, None)
  private val pathway2to5: Pathway = Pathway("go to 5", finalStoryNode5, None)
  private val pathway2to6: Pathway = Pathway("go to 6", finalStoryNode6, None)
  private val pathway3to6: Pathway = Pathway("go to 6", finalStoryNode6, None)

  private val middleNode1: StoryNode = StoryNode(1, "middle-4", Set(pathway1to4))
  private val middleNode2: StoryNode = StoryNode(2, "middle-5", Set(pathway2to5, pathway2to6))
  private val middleNode3: StoryNode = StoryNode(3, "middle-6", Set(pathway3to6))


  private val pathway0to1: Pathway = Pathway("go to 1", middleNode1, None)
  private val pathway0to2: Pathway = Pathway("go to 2", middleNode2, None)
  private val pathway0to3: Pathway = Pathway("go to 3", middleNode3, None)

  private val startingNode0: StoryNode = StoryNode(0, "start-0", Set(pathway0to1, pathway0to2, pathway0to3))

  val storyModel: StoryModel = StoryModel(player, startingNode0)
}
