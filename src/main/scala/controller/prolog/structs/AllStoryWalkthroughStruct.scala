package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates.AllStoryWalkthroughPredicateName
import controller.prolog.util.PrologImplicits.RichTerm

/**
 * [[alice.tuprolog.Struct]] used fot the all_story_walkthrough(S, R) predicate.
 *
 * @param _startId S in prolog predicate.
 * @param _result  R in prolog predicate.
 */
case class AllStoryWalkthroughStruct(private val _startId: Term, private val _result: Term)
  extends Struct(AllStoryWalkthroughPredicateName, _startId, _result) {

  def startId: Int = _startId.toInt

  def result: List[List[String]] = _result.toList(t1 => t1.toList(t2 => t2.toFormattedString))
}