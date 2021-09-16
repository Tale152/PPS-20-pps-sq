package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates.AllFinalNodesSolutionsPredicateName
import controller.prolog.util.PrologImplicits.RichTerm

/**
 * [[alice.tuprolog.Struct]] used fot the all_final_nodes_solution(S, L) predicate.
 *
 * @param _startId         S in prolog predicate.
 * @param _allCrossedNodes L in prolog predicate.
 */
case class AllFinalNodeSolutionsStruct(private val _startId: Term, private val _allCrossedNodes: Term)
  extends Struct(AllFinalNodesSolutionsPredicateName, _startId, _allCrossedNodes) {

  def startId: Int = _startId.toInt

  def allCrossedNodes: Seq[Seq[Int]] = _allCrossedNodes.toSeq(t => t.toIntSeq)
}
