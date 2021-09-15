package suites

import controller.prolog.predicates._
import org.scalatest.Suites

class PrologEngineSuite extends Suites(
  new AllStoryWalkthroughPredicateTest,
  new FindAllSolutionPredicateTest,
  new PathPredicateTest,
  new PathwayDescriptionPredicateTest,
  new ReachAllFinalNodesPredicateTest,
  new StoryNodePredicateTest,
  new StoryNodeNarrativePredicateTest,
  new StoryWalkthroughPredicateTest,
  new WalkthroughPredicateTest
)
