package suites

import controller.prolog.predicates._
import org.scalatest.Suites

class PrologEngineSuite extends Suites(
  new AllStoryWalkthroughPredicateTest,
  new FindAllSolutionPredicateTest,
  new PathStructTest,
  new PathwayDescriptionPredicateTest,
  new ReachAllFinalNodesPredicateTest,
  new StoryNodeStructTest,
  new StoryNodeNarrativePredicateTest,
  new StoryWalkthroughPredicateTest,
  new WalkthroughPredicateTest
)
