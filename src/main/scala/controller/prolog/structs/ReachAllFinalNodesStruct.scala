package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates.ReachAllFinalNodesPredicateName
import controller.prolog.util.PrologImplicits.RichTerm

/**
 * [[alice.tuprolog.Struct]] used fot the reach_all_final_nodes(S, L) predicate.
 *
 * @param _startId      S in prolog predicate.
 * @param _crossedNodes L in prolog predicate.
 */
case class ReachAllFinalNodesStruct(_startId: Term, _crossedNodes: Term)
  extends Struct(ReachAllFinalNodesPredicateName, _startId, _crossedNodes) {

  def startId: Int = _startId.toInt

  def crossedNodes: Seq[Int] = _crossedNodes.toIntSeq

}
