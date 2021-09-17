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
    val PathwayDescriptionPredicateName: String = "pathway_description"
    val StoryNodeNarrativePredicateName: String = "story_node_narrative"
    val WalkthroughPredicateName: String = "walkthrough"
    val StoryWalkthroughPredicateName: String = "story_walkthrough"
    val AllStoryWalkthroughPredicateName: String = "all_story_walkthrough"

    object Records {
      val PathwayRecord: String = "pathway"
    }

  }


}
