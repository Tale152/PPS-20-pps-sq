package model.nodes.util

import model.characters.Enemy
import model.characters.properties.stats.{Stat, StatModifier, StatName}
import model.items.{ConsumableItem, EquipItem, EquipItemType, Item, KeyItem}
import model.nodes.{ItemEvent, Pathway, StatEvent, StoryNode}
import model.nodes.Event

import scala.util.Random
import scala.collection.mutable.{Set => MutableSet}

/**
 * Component used to create a Random [[model.nodes.StoryNode]]
 */
object RandomStoryNodeGenerator {

  private object RandomStoryParams {
    val MaxNodesInLayer = 5
    val Layers = 7
    val EnemyProbability = 5
    val EventProbability = 5
  }

  import model.nodes.util.RandomStoryNodeGenerator.RandomStoryParams._

  object Generator {

    private object Utility {
      def rnd(max: Int): Int = Random.nextInt(max) + 1

      def getMaxId(nodes: Seq[StoryNode]): Int = nodes.map(n => n.id).max

      def setEnemy(): Option[Enemy] = {

        val statValue: Int = 5
        val maxPossibleHealth: Int = 100
        val stats: Set[Stat] = Set(
          Stat(statValue, StatName.Strength),
          Stat(statValue, StatName.Constitution),
          Stat(statValue, StatName.Wisdom),
          Stat(statValue, StatName.Charisma),
          Stat(statValue, StatName.Intelligence),
          Stat(statValue, StatName.Dexterity)
        )
        rnd(EnemyProbability) match {
          case 1 => Some(Enemy("Giorno Giovanna", rnd(maxPossibleHealth), stats))
          case _ => None
        }
      }

      def setEvents(): List[Event] = {
        var res: List[Event] = List()
        if (rnd(EventProbability) == 1) {
          res = ItemEvent(
            "This is an item event, it is used to notify the user that an item was found.",
            randomItem()
          ) :: res
        }
        if (rnd(EventProbability) == 1) {
          res = StatEvent(
            "This is a stat event, it is used to notify the user that some stats have changed.",
            StatModifier(StatName.Strength, i => i + 5)
          ) :: res
        }
        res
      }

      def randomItem(): Item = {
        rnd(3) match {
          case 1 => KeyItem("key-item", "Key items can't be used but are useful during the story.")
          case 2 => ConsumableItem(
            "consumable-item",
            "Consumable items will disappear after use.",
            c => c.properties.health.currentPS += 10
          )
          case 3 => EquipItem(
            "equip-item",
            "Equip items can be equipped and will boost your stats.",
            Set(StatModifier(StatName.Strength, v => v + 1)),
            EquipItemType.Weapon
          )
        }
      }
    }

    import model.nodes.util.RandomStoryNodeGenerator.Generator.Utility._

    private def generateLayers(depth: Int): Seq[StoryNode] = depth match {
      case 0 => generateLastLayer()
      case _ => generateIntermediateLayer(depth)
    }

    private def genPathwaysToNextLayerNode(node: StoryNode, nCurrentLayersNodes: Int): MutableSet[Pathway] =
      (for (_ <- 0 until Random.nextInt(nCurrentLayersNodes) + 1)
        yield Pathway("go to node " + node.id, node, None)).toSet.to[MutableSet]

    private def genPathwaysToAllNextLayerNodes(nextLayer: Seq[StoryNode],
                                               nCurrentLayerNodes: Int): Seq[MutableSet[Pathway]] =
      for (node <- nextLayer) yield genPathwaysToNextLayerNode(node, nCurrentLayerNodes)

    private def generateIntermediateLayer(depth: Int): Seq[StoryNode] = {
      val nextLayer = generateLayers(depth - 1)
      val startingId = getMaxId(nextLayer) + 1
      val nCurrentLayerNodes = rnd(MaxNodesInLayer)
      val pathwaysToNextNodes: Seq[MutableSet[Pathway]] = genPathwaysToAllNextLayerNodes(nextLayer, nCurrentLayerNodes)
      var res = Seq.empty[StoryNode]
      for (id <- startingId until startingId + nCurrentLayerNodes) {
        var newNodePathways = Seq.empty[Pathway]
        for (pathwaysToNode <- pathwaysToNextNodes) {
          if (pathwaysToNode.nonEmpty) {
            val assignedPathway = pathwaysToNode.last
            newNodePathways = newNodePathways union Seq(assignedPathway)
            pathwaysToNode.remove(assignedPathway)
          }
        }
        val narrative =
          if (newNodePathways.isEmpty) "final node " + id else "node " + id + ", max remaining layers " + depth

        res = res :+ StoryNode(id, narrative, setEnemy(), newNodePathways.toSet, setEvents())
      }
      res
    }

    private def generateLastLayer(): IndexedSeq[StoryNode] =
      for (x <- 0 until rnd(MaxNodesInLayer)) yield StoryNode(x, "final node " + x, None, Set.empty, List())

    def generate(): StoryNode = {
      val generated = generateLayers(Layers - 1)
      val pathways: Seq[Pathway] = for (node <- generated) yield Pathway("go to node " + node.id, node, None)

      StoryNode(getMaxId(generated) + 1,
        "starting node, max remaining layers " + Layers,
        None,
        pathways.toSet,
        setEvents())
    }
  }
}
