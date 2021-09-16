package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates._
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
      case s: String if s == ReachAllFinalNodesPredicateName =>
        ReachAllFinalNodesStruct(struct.getTerm(0), struct.getTerm(1))
      case s: String if s == AllFinalNodesSolutionsPredicateName =>
        AllFinalNodeSolutionsStruct(struct.getTerm(0), struct.getTerm(1))
      case s: String if s == PathwayDescriptionPredicateName =>
        PathwayDescriptionStruct(struct.getTerm(0), struct.getTerm(1), struct.getTerm(2))
      case s: String if s == StoryNodeNarrativePredicateName =>
        StoryNodeNarrativeStruct(struct.getTerm(0), struct.getTerm(1))
      case s: String if s == WalkthroughPredicateName =>
        WalkthroughStruct(struct.getTerm(0), struct.getTerm(1))
    }
  }
}
