package model.items

import model.characters.Character
import model.characters.properties.stats.StatModifier
import model.items.EquipItemType.EquipItemType

import scala.::

/**
 * Trait that represents an Item.
 */
trait Item extends Serializable {
  val name: String
  val description: String
  def use(target: Character): Unit
}

/**
 * The abstract class that describe an item.
 * @param name the item name.
 * @param description the item description.
 */
abstract class AbstractItem(override val name: String,
                            override val description: String) extends Item {
  /**
   * Template method that use [[model.items.AbstractItem#applyEffect(java.lang.Character)]]
   * and [[model.items.AbstractItem#postEffect(java.lang.Character)]].
   * @param target the target of the item effect.
   */
  override def use(target: Character): Unit = {
    applyEffect(target)
    postEffect(target)
  }

  def applyEffect(target: Character): Unit
  def postEffect(target: Character): Unit
}

/**
 * An item that can't be used, it may be useful in the storyline.
 * @param name the item name.
 * @param description the item description.
 */
case class KeyItem(override val name: String,
                   override val description: String) extends AbstractItem(name, description) {
  override def applyEffect(target: Character): Unit = { /*does nothing*/ }

  override def postEffect(target: Character): Unit = { /*does nothing*/ }
}

/**
 * An item that can be consumed during the storyline.
 * @param name the item name.
 * @param description the item description.
 * @param consumableStrategy the actual effect of the item when it's consumed.
 */
case class ConsumableItem(override val name: String,
                          override val description: String,
                          owner: Character,
                          consumableStrategy: Character => Unit) extends AbstractItem(name, description) {
  override def applyEffect(target: Character): Unit = consumableStrategy(target)

  override def postEffect(target: Character): Unit = owner.inventory -= this
}

/**
 * An item that can be equipped during the storyline.
 * @param name the item name.
 * @param description the item description.
 * @param statModifiers a set of stat modifier that are applied when they are equipped.
 * @param equipItemType the item equip type.
 */
case class EquipItem(override val name: String,
                     override val description: String,
                     statModifiers: Set[StatModifier],
                     equipItemType: EquipItemType) extends AbstractItem(name, description) {
  override def applyEffect(target: Character): Unit = target.equippedItems += this

  override def postEffect(target: Character): Unit = target.equippedItems -= this
}
