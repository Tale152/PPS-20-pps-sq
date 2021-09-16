package suites

import controller.prolog.structs._
import org.scalatest.Suites

class PrologEngineSuite extends Suites(
  new AllStoryWalkthroughStructTest,
  new AllFinalNodeSolutionsStructTest,
  new PathStructTest,
  new PathwayDescriptionStructTest,
  new ReachAllFinalNodesStructTest,
  new StoryNodeStructTest,
  new StoryNodeNarrativeStructTest,
  new StoryWalkthroughStructTest,
  new WalkthroughStructTest
)
