package model.characters

import model.characters.properties.PropertiesContainer
import model.characters.properties.stats.{Stat, StatName}
import model.items.{EquipItem, Item}

/**
 * Trait that represents a Character.
 */
sealed trait Character extends Serializable {
  val name: String
  val properties: PropertiesContainer

  def inventory: List[Item]

  def inventory_=(itemList: List[Item]): Unit

  def equippedItems: Set[EquipItem]

  def equippedItems_=(equippedItemSet: Set[EquipItem]): Unit
}

/**
 * Abstract class that represents an AbstractCharacter.
 *
 * @param name  the name of the Character.
 * @param maxPS the maximum number of PS with full life.
 * @param stats a set of predefined stats of the Character.
 */
abstract class AbstractCharacter(override val name: String, maxPS: Int, private val stats: Set[Stat])
  extends Character {
  require(
    name != ""
    && maxPS > 0
    && stats.size == StatName.setOfValidStats().size
    && StatName.setOfValidStats().subsetOf(stats.map(s => s.statName)))

  override val properties: PropertiesContainer = PropertiesContainer(maxPS, stats)
  override var equippedItems: Set[EquipItem] = Set()
  private var _inventory: List[Item] = List()

  def inventory: List[Item] = _inventory

  def inventory_=(itemList: List[Item]): Unit = _inventory = itemList.sorted

}

/**
 *
 * Case class that represents the one and only protagonist of the game.
 *
 * @param name  the name of the Player.
 * @param maxPS the maximum number of PS with full life.
 * @param stats a set of predefined stats of the Player.
 */
case class Player(override val name: String, maxPS: Int, private val stats: Set[Stat])
  extends AbstractCharacter(name, maxPS, stats)

/**
 * Case class that represents every enemy of the game.
 *
 * @param name  the name of the Enemy.
 * @param maxPS the maximum number of PS with full life
 * @param stats a set of predefined stats of the Enemy
 */
case class Enemy(override val name: String, maxPS: Int, private val stats: Set[Stat])
  extends AbstractCharacter(name, maxPS, stats)