package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates.StoryWalkthroughPredicateName
import controller.prolog.util.PrologImplicits.RichTerm

/**
 * [[alice.tuprolog.Struct]] used fot the story_walkthrough(S, W) predicate.
 *
 * @param _startId     IDs in prolog predicate.
 * @param _walkthrough W in prolog predicate.
 */
case class StoryWalkthroughStruct(private val _startId: Term, private val _walkthrough: Term)
  extends Struct(StoryWalkthroughPredicateName, _startId, _walkthrough) {

  def startId: Int = _startId.toInt

  def walkthrough: Seq[String] = _walkthrough.toSeq(t => t.toFormattedString)

}

