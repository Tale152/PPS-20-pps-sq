package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates.PathwayDescriptionPredicateName
import controller.prolog.util.PrologImplicits.RichTerm

/**
 * [[alice.tuprolog.Struct]] used fot the pathway_description(S, E, D) predicate.
 *
 * @param _startId     S in prolog predicate.
 * @param _endId       E in prolog predicate.
 * @param _description D in prolog predicate.
 */
case class PathwayDescriptionStruct(private val _startId: Term,
                                    private val _endId: Term,
                                    private val _description: Term)
  extends Struct(PathwayDescriptionPredicateName, _startId, _endId, _description) {

  def startId: Int = _startId.toInt

  def endId: Int = _endId.toInt

  def description: String = _description.fromPrologToString

}

