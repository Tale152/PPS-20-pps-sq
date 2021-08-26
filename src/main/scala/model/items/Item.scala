package model.items

import model.characters.Character
import model.characters.properties.stats.StatModifier
import model.items.EquipItemType.EquipItemType

/**
 * Trait that represents an Item.
 */
trait Item extends Serializable with Ordered[Item] {
  val name: String
  val description: String
  def use(owner: Character)(target: Character = owner): Unit
}

/**
 * The abstract class that describe an item.
 * @param name the item name.
 * @param description the item description.
 */
abstract class AbstractItem(override val name: String,
                            override val description: String) extends Item {

  require(name != null && name.trim.nonEmpty  && description != null && description.trim.nonEmpty)
  /**
   * Template method that use [[model.items.AbstractItem#applyEffect(java.lang.Character)]]
   * and [[model.items.AbstractItem#postEffect(java.lang.Character)]].
   * @param owner thw owner of the item.
   * @param target the target of the item effect.
   */
  override def use(owner: Character)(target: Character = owner): Unit = {
    applyEffect(owner)(target)
    postEffect(owner)(target)
  }

  def applyEffect(owner: Character)(target: Character = owner): Unit
  def postEffect(owner: Character)(target: Character = owner): Unit

  override def compare(that: Item): Int = {
   if(this.isInstanceOf[that.type]) {
     this.name compare that.name //compare alphabetically
   } else {
     _compareByPriorityType(that)
   }
  }

  private def _compareByPriorityType(that: Item): Int = {
    this match {
      case _: KeyItem => 1
      case _: ConsumableItem => -1
      case _: EquipItem => if (that.isInstanceOf[ConsumableItem]) 1 else -1
      case _ => throw new IllegalArgumentException("Supplied item class does not exist")
    }
  }
}

/**
 * An item that can't be used, it may be useful in the storyline.
 * @param name the item name.
 * @param description the item description.
 */
case class KeyItem(override val name: String,
                   override val description: String) extends AbstractItem(name, description) {
  override def applyEffect(owner: Character)(target: Character = owner): Unit = { /*does nothing*/ }

  override def postEffect(owner: Character)(target: Character = owner): Unit = { /*does nothing*/ }
}

/**
 * An item that can be consumed during the storyline.
 * @param name the item name.
 * @param description the item description.
 * @param consumableStrategy the actual effect of the item when it's consumed.
 */
case class ConsumableItem(override val name: String,
                          override val description: String,
                          consumableStrategy: Character => Unit) extends AbstractItem(name, description) {
  override def applyEffect(owner: Character)(target: Character = owner): Unit = consumableStrategy(target)

  override def postEffect(owner: Character)(target: Character = owner): Unit =
    owner.inventory = owner.inventory.filter(_ != this)
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
  override def applyEffect(owner: Character)(target: Character = owner): Unit = {
    val equippedItemToSwap : Option[EquipItem] = target.equippedItems.find(i => i.equipItemType == equipItemType)
    if(equippedItemToSwap.isDefined) {
      target.equippedItems -= equippedItemToSwap.get
      if(this.ne(equippedItemToSwap.get)) {
        target.equippedItems += this
      }
    } else {
      target.equippedItems += this
    }
  }

  override def postEffect(owner: Character)(target: Character = owner): Unit = { /*does nothing*/ }
}
