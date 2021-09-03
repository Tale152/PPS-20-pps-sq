package model.nodes

import model.characters.Enemy

/**
 * Trait that represents a story node, which is used to have a reference of all the current possible pathways and
 * of what is happening in the story.
 */

trait StoryNode extends Serializable{
  def id: Int

  def narrative: String

  def enemy: Option[Enemy]

  def pathways: Set[Pathway]

  def events: List[Event]
}

object StoryNode {

  /**
   * Implementation of StoryNode.
   *
   * @param id        is the unique id of the node.
   * @param narrative is the text that the player will read, which is the actual story.
   * @param enemy     is the enemy that might be found in a story node, with whom the player must battle.
   * @param pathways  are the possible pathways that the player can see and choose, to progress in the story.
   * @param events    an ordered list containing eventual events to handle while entering this node.
   * @return the story node.
   */
  def apply(id: Int,
            narrative: String,
            enemy: Option[Enemy],
            pathways: Set[Pathway],
            events: List[Event]): StoryNode = new StoryNodeImpl(id, narrative, enemy, pathways, events)

  private class StoryNodeImpl(override val id: Int,
                              override val narrative: String,
                              override val enemy: Option[Enemy],
                              override val pathways: Set[Pathway],
                              override val events: List[Event]) extends StoryNode {
    Checker.check(id, narrative, enemy, pathways, events)
  }
}

trait MutableStoryNode extends StoryNode {
  var id: Int
  var narrative: String
  var enemy: Option[Enemy]
  var mutablePathways: Set[MutablePathway]
  var events: List[Event]
}

object MutableStoryNode {

  def apply(id: Int,
            narrative: String,
            enemy: Option[Enemy],
            pathways: Set[MutablePathway],
            events: List[Event]): MutableStoryNode =
    new MutableStoryNodeImpl(id, narrative, enemy, pathways, events)

  private class MutableStoryNodeImpl(override var id: Int,
                              override var narrative: String,
                              override var enemy: Option[Enemy],
                              override var mutablePathways: Set[MutablePathway],
                              override var events: List[Event]) extends MutableStoryNode {
    Checker.check(id, narrative, enemy, pathways, events)

    override def pathways: Set[Pathway] = for(p <- mutablePathways) yield p.asInstanceOf[Pathway]
  }
}

private object Checker {
  def check(id: Int,
            narrative: String,
            enemy: Option[Enemy],
            pathways: Set[Pathway],
            events: List[Event]): Unit =
    require(
      !id.isNaN &&
        narrative != null && narrative.trim.nonEmpty &&
        enemy != null &&
        pathways.forall(p => !pathways.exists(o => !o.eq(p) && o.destinationNode.eq(p.destinationNode))) &&
        containsOnePathwayWithNoCondition(pathways) &&
        events != null
    )

  private def containsOnePathwayWithNoCondition(pathways: Set[Pathway]): Boolean =
    if (pathways.nonEmpty) pathways.exists(p => p.prerequisite.isEmpty) else true
}