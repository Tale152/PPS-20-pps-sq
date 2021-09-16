package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates.StoryNodePredicateName
import controller.prolog.util.PrologImplicits.RichTerm

/**
 * [[alice.tuprolog.Struct]] used fot the story_node(I, N, P) predicate.
 *
 * @param _id        I in prolog predicate.
 * @param _narrative N in prolog predicate.
 * @param _pathways  P in prolog predicate.
 */
case class StoryNodeStruct(_id: Term, _narrative: Term, _pathways: Term)
  extends Struct(StoryNodePredicateName, _id, _narrative, _pathways) {
  def id: Int = _id.toInt

  def narrative: String = _narrative.toString

  def pathways: Set[(Int, String)] = _pathways.toSeq(pathwayToTuple).toSet

  private val pathwayToTuple: Term => (Int, String) =
    t => (t.extractTerm(0).toInt, t.extractTerm(1).toString)
}
