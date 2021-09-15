package controller.prolog.engine.predicates

import alice.tuprolog.{Struct, Term}

object Predicates {

  import controller.prolog.engine.predicates.PrologPredicatesNames.Predicates._

  case class StoryNodePredicate(id: Term, narrative: Term, pathways: Term)
    extends Struct(StoryNodePredicateName, id, narrative, pathways)

  case class PathPredicate(startId: Term, endId: Term, crossedIds: Term)
    extends Struct(PathPredicateName, startId, endId, crossedIds)

}
