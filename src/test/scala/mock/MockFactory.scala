package mock

import model.StoryModel
import model.characters.Player
import model.characters.properties.stats.{Stat, StatName}
import model.nodes.{Pathway, StoryNode}

object MockFactory {

  def mockSetOfStats(): Set[Stat] = {
    Set(Stat(1, StatName.Speed))
  }

  def mockPlayer(maxPS: Int): Player = {
    Player("player", maxPS, mockSetOfStats())
  }

  def mockStoryNode(): StoryNode = {
    val destinationNode: StoryNode = StoryNode(1, "narrative", None, Set.empty, List())
    val pathway: Pathway = Pathway("description", destinationNode, None)
    StoryNode(0, "narrative", None, Set(pathway), List())
  }

  def mockStoryModel(playerMaxPS: Int): StoryModel = {
    StoryModel(mockPlayer(playerMaxPS), mockStoryNode())
  }

}
