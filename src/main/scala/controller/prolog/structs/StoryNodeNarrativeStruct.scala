package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates.StoryNodeNarrativePredicateName
import controller.prolog.util.PrologImplicits.RichTerm

/**
 * [[alice.tuprolog.Struct]] used fot the story_node_narrative(ID, N) predicate.
 * @param _id ID in prolog predicate.
 * @param _narrative N in prolog predicate.
 */
case class StoryNodeNarrativeStruct(private val _id: Term, private val _narrative: Term)
  extends Struct(StoryNodeNarrativePredicateName, _id, _narrative) {

  def id: Int =  _id.toInt

  def narrative: String = _narrative.toString
}
