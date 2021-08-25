package model.items

object EquipItemType {

  /**
   * Trait used by [[model.items.EquipItem]] to specify the armor type.
   */
  trait EquipItemType

  case object Armor extends EquipItemType

  case object Weapon extends EquipItemType

  case object Helmet extends EquipItemType

  case object Gloves extends EquipItemType

  case object Boots extends EquipItemType

  case object Necklace extends EquipItemType

  case object Socks extends EquipItemType

}
