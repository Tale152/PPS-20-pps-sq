package model.characters

import model.characters.properties.PropertiesContainer
import model.characters.properties.stats.{Stat, StatName}
import model.items.{EquipItem, Item}

/**
 * Trait that represents a Character.
 */
sealed trait Character extends Serializable {

  /**
   * The name of the Character.
   */
  val name: String

  /**
   * The instance of [[PropertiesContainer]] associated with this Character.
   */
  val properties: PropertiesContainer

  /**
   * @return a list of Item owned by the Character
   */
  def inventory: List[Item]

  /**
   * Changes the list of Item owned by the Character.
   * @param itemList the new list of Item owned by the Character
   */
  def inventory_=(itemList: List[Item]): Unit

  /**
   * @return a set containing all EquipItem equipped to the Character
   */
  def equippedItems: Set[EquipItem]

  /**
   * Changes the set of EquipItem equipped to the Character.
   * @param equippedItemSet the new Set of EquipItem equipped to the Character
   */
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
      Option(name).nonEmpty
      && name.trim.nonEmpty
      && maxPS > 0
      && stats.size == StatName.setOfValidStats.size
      && StatName.setOfValidStats.subsetOf(stats.map(s => s.statName))
  )

  override val properties: PropertiesContainer = PropertiesContainer(maxPS, stats)
  override var equippedItems: Set[EquipItem] = Set()
  private var _inventory: List[Item] = List()

  def inventory: List[Item] = _inventory

  def inventory_=(itemList: List[Item]): Unit = _inventory = itemList.sorted

}

/**
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