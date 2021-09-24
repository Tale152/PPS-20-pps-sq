package model.nodes

import model.characters.Enemy

/**
 * Trait that represents a story node, which is used to have a reference of all the current possible pathways and
 * of what is happening in the story.
 */

sealed trait StoryNode extends Serializable {

  /**
   * @return the story node ID.
   */
  val id: Int

  /**
   * @return the story node narrative.
   */
  def narrative: String

  /**
   * @return A [[scala.Option]] that contains the enemy if present.
   */
  def enemy: Option[Enemy]

  /**
   * @return A set of possible [[model.nodes.Pathway]] reachable from this node.
   */
  def pathways: Set[Pathway]

  /**
   * @return A list of [[model.nodes.Event]] contained in the node.
   */
  def events: List[Event]

  /**
   * Remove all the [[model.nodes.Event]] in the story node.
   */
  def removeAllEvents(): Unit
}

object StoryNode {

  /**
   * Implementation of StoryNode.
   *
   * @param id        is the unique id of the node.
   * @param narrative is the text that the player will read, which is the actual story.
   * @param enemy     is the enemy that might be found in a story node, with whom the player must battle.
   * @param pathways  are the possible pathways that the player can see and choose, to progress in the story.
   * @param eventList    an ordered list containing eventual events to handle while entering this node.
   * @return the story node.
   */
  def apply(id: Int,
            narrative: String,
            enemy: Option[Enemy],
            pathways: Set[Pathway],
            eventList: List[Event]): StoryNode = new StoryNodeImpl(id, narrative, enemy, pathways, eventList)

  private class StoryNodeImpl(override val id: Int,
                              override val narrative: String,
                              override val enemy: Option[Enemy],
                              override val pathways: Set[Pathway],
                              eventList: List[Event]) extends StoryNode {

    ArgsChecker.check(id, narrative, enemy, pathways, eventList)

    private var _events: List[Event] = eventList

    override def events: List[Event] = _events

    override def removeAllEvents(): Unit = _events = List()
  }

  sealed trait MutableStoryNode extends StoryNode {
    val id: Int
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

    private class MutableStoryNodeImpl(override val id: Int,
                                       override var narrative: String,
                                       override var enemy: Option[Enemy],
                                       override var mutablePathways: Set[MutablePathway],
                                       override var events: List[Event]) extends MutableStoryNode {
      require(mutablePathways != null)
      ArgsChecker.check(id, narrative, enemy, pathways, events)

      override def pathways: Set[Pathway] = for (p <- mutablePathways) yield p.asInstanceOf[Pathway]

      override def removeAllEvents(): Unit =  events = List()
    }
  }

  private object ArgsChecker {
    def check(id: Int, narrative: String, enemy: Option[Enemy], pathways: Set[Pathway], eventList: List[Event]): Unit =
      require(
          !id.isNaN &&
          narrative != null && narrative.trim.nonEmpty &&
          enemy != null &&
          pathways.forall(p => !pathways.exists(o => !o.eq(p) && o.destinationNode.eq(p.destinationNode))) &&
          containsOnePathwayWithNoCondition(pathways) &&
          eventList != null
      )

    private def containsOnePathwayWithNoCondition(pathways: Set[Pathway]): Boolean =
      if (pathways.nonEmpty) pathways.exists(p => p.prerequisite.isEmpty) else true
  }

}
