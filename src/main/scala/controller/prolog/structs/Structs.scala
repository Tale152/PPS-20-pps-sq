package controller.prolog.structs

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates._

import scala.language.implicitConversions
/**
 * Factory Object used to map [[alice.tuprolog.Term]] results to the correct class.
 */
object Structs {

  def apply[A <: Term](term: Term): Struct = {
    val struct = term.asInstanceOf[Struct]
    require(struct.isStruct)

    /**
     * Notice this implicit used to avoid the repetition of [[alice.tuprolog.Struct#getTerm(int)]] call method.
     * @param integer the index of the struct.
     * @return the extracted term from the struct.
     */
    implicit def extractTermFromInt(integer: Int): Term = struct.getTerm(integer)

    struct.getName match {
      case StoryNodePredicateName =>
        StoryNodeStruct(0, 1, 2)
      case PathPredicateName =>
        PathStruct(0, 1, 2)
      case ReachAllFinalNodesPredicateName =>
        ReachAllFinalNodesStruct(0, 1)
      case AllFinalNodesSolutionsPredicateName =>
        AllFinalNodeSolutionsStruct(0, 1)
      case PathwayDescriptionPredicateName =>
        PathwayDescriptionStruct(0, 1, 2)
      case StoryNodeNarrativePredicateName =>
        StoryNodeNarrativeStruct(0, 1)
      case WalkthroughPredicateName =>
        WalkthroughStruct(0, 1)
      case StoryWalkthroughPredicateName =>
        StoryWalkthroughStruct(0, 1)
      case AllStoryWalkthroughPredicateName =>
        AllStoryWalkthroughStruct(0, 1)
    }
  }

}
