package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates.PathPredicateName
import controller.prolog.util.PrologImplicits.RichTerm

/**
 * [[alice.tuprolog.Struct]] used fot the path(S, E, C) predicate.
 *
 * @param _startId    S in prolog predicate.
 * @param _endId      E in prolog predicate.
 * @param _crossedIds C in prolog predicate.
 */
case class PathStruct(private val _startId: Term, private val _endId: Term, private val _crossedIds: Term)
  extends Struct(PathPredicateName, _startId, _endId, _crossedIds) {

  def startId: Int = _startId.toInt

  def endId: Int = _endId.toInt

  def crossedIds: List[Int] = _crossedIds.toIntList
}
