package controller.prolog.structs

/**
 * Utility object that contains Prolog Predicate and Record names.
 */
object StructsNames {

  object Predicates {
    val StoryNodePredicateName: String = "story_node"
    val PathPredicateName: String = "path"
    val ReachAllFinalNodesPredicateName: String = "reach_all_final_nodes"
    val AllFinalNodesSolutionsPredicateName: String = "all_final_nodes_solutions"

    object Records {
      val PathwayRecord: String = "pathway"
    }

  }


}
