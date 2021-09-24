package controller.editor.graph.util

import org.graphstream.graph.{Edge, Node}

/**
 * Utility object used to put a style on a GraphStream's Graph element.
 */
object ElementStyle {

  private val StyleAttribute: String = "ui.style"

  private val TextAlignment: String = "under"
  private val NodeSize: Int = 15
  private val ArrowWidth: Int = 5
  private val ArrowHeight: Int = 15

  private val RouteNodeShape: String = "diamond"
  private val MidNodeShape: String = "circle"
  private val FinalNodeShape: String = "cross"

  private val EventNodeColor: String = "blue"
  private val EnemyNodeColor: String = "red"
  private val BaseNodeColor: String = "black"

  private val UnconditionalEdge: String = "black"
  private val ConditionalEdge: String = "purple"

  /**
   * Used to put style on a GraphStream's Graph node that represents a StoryNode that isn't route in a story.
   * @param node the GraphStream's Graph Node to put a style on
   * @param events if at leas one event is present in the StoryNode
   * @param enemy if an Enemy is present in the StoryNode
   * @param finalNode if the StoryNode doesn't originates any pathway
   */
  def decorateNode(node: Node, events: Boolean, enemy: Boolean, finalNode: Boolean): Unit = {
    if (finalNode) {
      node.setAttribute(StyleAttribute, getNodeStyle(events, enemy) + "shape: " + FinalNodeShape + ";")
    } else {
      node.setAttribute(StyleAttribute, getNodeStyle(events, enemy) + "shape: " + MidNodeShape + ";")
    }
  }

  /**
   * Used to put style on a GraphStream's Graph node that represents a StoryNode that is route in a story.
   * @param node the GraphStream's Graph Node to put a style on
   * @param events if at leas one event is present in the StoryNode
   * @param enemy if an Enemy is present in the StoryNode
   */
  def decorateRouteNode(node: Node, events: Boolean, enemy: Boolean): Unit =
    node.setAttribute(StyleAttribute, getNodeStyle(events, enemy) + "shape: " + RouteNodeShape + ";")

  private def getNodeStyle(events: Boolean, enemy: Boolean): String = {
    var style = "text-alignment: " + TextAlignment + "; size: " + NodeSize + "px;"
    if (events) {
      if (enemy) {
        style += "fill-mode:gradient-horizontal; fill-color: " + EventNodeColor + ", " + EnemyNodeColor + ";"
      } else {
        style += "fill-color: " + EventNodeColor + ";"
      }
    } else if (enemy) {
      style += "fill-color: " + EnemyNodeColor + ";"
    } else {
      style += "fill-color: " + BaseNodeColor + ";"
    }
    style
  }

  /**
   * Used to put style on a GraphStream's Graph edge that represents a Pathway in a story.
   * @param edge the GraphStream's Graph Edge to put a style on
   * @param hasPrerequisite if the Pathway has a prerequisite
   */
  def decorateEdge(edge: Edge, hasPrerequisite: Boolean): Unit =
    if (hasPrerequisite) {
      edge.setAttribute(StyleAttribute, "fill-color: " + ConditionalEdge +
        "; arrow-shape: arrow; arrow-size: " + ArrowHeight  + "px, " + ArrowWidth  + "px;")
    } else {
      edge.setAttribute(StyleAttribute, "fill-color: " + UnconditionalEdge +
        "; arrow-shape: arrow; arrow-size: " + ArrowHeight + "px, " + ArrowWidth  + "px;")
    }
}
