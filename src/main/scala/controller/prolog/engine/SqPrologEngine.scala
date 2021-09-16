package controller.prolog.engine

import alice.tuprolog._
import controller.prolog.engine.EngineTheory.{EngineFileTheory, StoryNodeTheory}
import controller.prolog.engine.structs.Structs
import model.nodes.StoryNode

/**
 * Create a Prolog Engine that has a finite set of facts. A [[model.nodes.StoryNode]] must be passed to generate the
 * [[controller.prolog.engine.EngineTheory.StoryNodeTheory]].
 *
 * @param storyNode the [[model.nodes.StoryNode]] used to generate the Theory of the Engine.
 */
case class SqPrologEngine(storyNode: StoryNode) {

  val engine: Prolog = new Prolog
  val engineTheory: Theory = EngineFileTheory()
  engineTheory.append(StoryNodeTheory(storyNode))
  engine.setTheory(engineTheory)

  def resolve[A <: Term](goal: A): Stream[A] = PrologEngineIterator(engine, goal).toStream

  def theory: String = engineTheory.toString

  /**
   * Create an iterator from the solutions that a [[alice.tuprolog.Prolog]] engine find for a specific goal.
   *
   * @param engine The Prolog engine.
   * @param goal   The goal to check.
   * @tparam A The Generic Term param used to force the solution to be the same type of the goal.
   */
  private case class PrologEngineIterator[A <: Term](engine: Prolog, goal: A)
    extends Iterator[A] {

    var optionalSolution: Option[A] = getNextSolution(_ => engine.solve(goal))

    override def hasNext: Boolean = optionalSolution.isDefined

    override def next(): A = {
      val solution: A = optionalSolution.get
      optionalSolution = getNextSolution(_ => engine.solveNext())
      solution
    }

    private def getNextSolution(getStrategy: Unit => SolveInfo): Option[A] = {
      try {
        Some(Structs(getStrategy().getSolution).asInstanceOf[A])
      } catch {
        case _: NoSolutionException => None
        case _: NoMoreSolutionException => None
      }
    }

  }
}
