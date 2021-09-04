package controller.editor.graph.util

import org.graphstream.graph.{Edge, Node}

object ElementStyle {

  private val StyleAttribute: String = "ui.style"

  private val TextAlignment: String = "under"
  private val NodeSize: Int = 20

  private val RouteNodeShape: String = "diamond"
  private val MidNodeShape: String = "circle"
  private val FinalNodeShape: String = "cross"

  private val EventNodeColor: String = "blue"
  private val EnemyNodeColor: String = "red"
  private val BaseNodeColor: String = "black"

  private val UnconditionalEdge: String = "black"
  private val ConditionalEdge: String = "purple"

  def decorateNode(node: Node, events: Boolean, enemy: Boolean, finalNode: Boolean): Unit = {
    if (finalNode) {
      node.setAttribute(StyleAttribute, getNodeStyle(events, enemy) + "shape: " + FinalNodeShape + ";")
    } else {
      node.setAttribute(StyleAttribute, getNodeStyle(events, enemy) + "shape: " + MidNodeShape + ";")
    }
  }

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

  def decorateEdge(edge: Edge, isConditional: Boolean): Unit =
    if (isConditional) {
      edge.setAttribute(StyleAttribute, "fill-color: " + ConditionalEdge + ";")
    } else {
      edge.setAttribute(StyleAttribute, "fill-color: " + UnconditionalEdge + ";")
    }
}
