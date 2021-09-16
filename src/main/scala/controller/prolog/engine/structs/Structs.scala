package controller.prolog.engine.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.engine.structs.StructsNames.Predicates.{PathPredicateName, StoryNodePredicateName}

/**
 * Factory Object used to map [[alice.tuprolog.Term]] results to the correct class.
 */
object Structs {
  def apply[A <: Term](term: Term): Struct = {
    val struct = term.asInstanceOf[Struct]
    require(struct.isStruct)
    struct.getName match {
      case s: String if s == StoryNodePredicateName =>
        StoryNodeStruct(struct.getTerm(0), struct.getTerm(1), struct.getTerm(2))
      case s: String if s == PathPredicateName =>
        PathStruct(struct.getTerm(0), struct.getTerm(1), struct.getTerm(2))
    }
  }
}
