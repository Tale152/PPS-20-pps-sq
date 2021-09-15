package controller.prolog.engine

import alice.tuprolog._
import controller.prolog.engine.EngineTheory.{EngineFileTheory, StoryNodeTheory}
import controller.prolog.engine.util.PrologEngineIterator
import model.nodes.StoryNode

/**
 * Create a Prolog Engine that has a finite set of facts. A [[model.nodes.StoryNode]] must be passed to generate the
 * [[controller.prolog.engine.EngineTheory.StoryNodeTheory]].
 *
 * @param storyNode the [[model.nodes.StoryNode]] used to generate the Theory of the Engine.
 */
case class SqPrologEngine(storyNode: StoryNode) extends (Term => Stream[Term]) {

  val engine: Prolog = new Prolog
  val engineTheory: Theory = EngineFileTheory()
  engineTheory.append(StoryNodeTheory(storyNode))
  engine.setTheory(engineTheory)

  override def apply(goal: Term): Stream[Term] = PrologEngineIterator(engine, goal).toStream

  def theory: String = engineTheory.toString
}
