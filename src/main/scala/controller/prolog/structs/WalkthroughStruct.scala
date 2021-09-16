package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates.WalkthroughPredicateName
import controller.prolog.util.PrologImplicits.RichTerm

/**
 * [[alice.tuprolog.Struct]] used fot the walkthrough(IDs, W) predicate.
 *
 * @param _idSeq       IDs in prolog predicate.
 * @param _walkthrough W in prolog predicate.
 */
case class WalkthroughStruct(private val _idSeq: Term, private val _walkthrough: Term)
  extends Struct(WalkthroughPredicateName, _idSeq, _walkthrough) {

  def idSeq: Seq[Int] = _idSeq.toIntSeq

  def walkthrough: Seq[String] = _walkthrough.toSeq(t => t.toFormattedString)

}
