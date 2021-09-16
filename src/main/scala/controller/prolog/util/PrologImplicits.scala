package controller.prolog.util

import alice.tuprolog.{Struct, Term}
import controller.prolog.structs.StructsNames.Predicates.StoryNodePredicateName
import controller.prolog.structs.StructsNames.Predicates.Records.PathwayRecord
import model.nodes.{Pathway, StoryNode}

import scala.language.implicitConversions

object PrologImplicits {
  /**
   * Implicit that let the String be treated as a [[alice.tuprolog.Term]].
   *
   * @param string the String to treat like a term.
   * @return The term that the string represents.
   */
  implicit def stringToTerm(string: String): Term = Term.createTerm(string)

  /**
   * Implicit that let the Int be treated as a [[alice.tuprolog.Term]].
   *
   * @param int the Int to treat like a term.
   * @return The term that the Int represents.
   */
  implicit def intToTerm(int: Int): Term = stringToTerm(int.toString)

  /**
   * Implicit that let a Seq be treated as a [[alice.tuprolog.Term]].
   *
   * @param sequence the sequence of generic objects.
   * @tparam T the generic type of the sequence.
   * @return a formatted string for the items.
   */
  implicit def seqToTerm[T](sequence: Seq[T]): Term = sequence.mkString("[", ",", "]")


  implicit class PrologStoryNode(storyNode: StoryNode) {

    /**
     * @return a string formatted as a prolog fact for the [[model.nodes.StoryNode]] class:
     *         story_node(id,narrative,[pathway]).
     */
    def toPrologFact: String = {
      StoryNodePredicateName + "(" + storyNode.id + ",'" + storyNode.narrative + "',[" +
        storyNode.pathways.map(p => p.toPrologRecord).mkString(",") + "])."
    }

  }

  implicit class PrologPathway(pathway: Pathway) {

    /**
     * @return a string formatted as a prolog record for the [[model.nodes.StoryNode]] class:
     *         pathway(toId, description).
     */
    def toPrologRecord: String = {
      PathwayRecord + "(" + pathway.destinationNode.id + ",'" + pathway.description + "')"
    }
  }

  /**
   * Implicit class to decorate [[alice.tuprolog.Term]] class with some brand new methods.
   *
   * @param term the [[alice.tuprolog.Term]] to use as a base for the implicit class.
   */
  implicit class RichTerm(term: Term) {
    /**
     * Extract a [[alice.tuprolog.Term]] from a structured Term.
     *
     * @param index the index of the element to extract.
     * @return the Term present in the index specified.
     */
    def extractTerm(index: Integer): Term =
      term.asInstanceOf[Struct].getArg(index).getTerm

    import collection.JavaConverters._

    /**
     * @return the term as a Seq of terms.
     */
    def toSeq: Seq[Term] = term.asInstanceOf[Struct].listIterator().asScala.toSeq

    /**
     * @param mappingFunction A mapping function to transform the term.
     * @tparam A The generic tye of the mapped Seq.
     * @return the term as a Seq of terms.
     */
    def toSeq[A](mappingFunction: Term => A): Seq[A] = term.toSeq.map(mappingFunction)

    /**
     * @return the term as a Seq of [[scala.Int]].
     */
    def toIntSeq: Seq[Int] = toSeq(t => t.toInt)

    /**
     * @return the term as a [[scala.Int]]
     */
    def toInt: Int = term.toString.toInt
  }
}