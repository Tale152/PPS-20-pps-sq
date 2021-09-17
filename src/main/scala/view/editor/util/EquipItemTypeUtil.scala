package view.editor.util

import model.items.EquipItemType.{Armor, Boots, EquipItemType, Gloves, Helmet, Necklace, Socks, Weapon}

object EquipItemTypeUtil {

  val BootsString: String = Boots.toString
  val ArmorString: String = Armor.toString
  val SocksString: String = Socks.toString
  val WeaponString: String = Weapon.toString
  val GlovesString: String = Gloves.toString
  val NecklaceString: String = Necklace.toString
  val HelmetString: String = Helmet.toString

  def getEquipItemType(selectedEquipItemTypeStr: String): EquipItemType = selectedEquipItemTypeStr match {
    case BootsString => Boots
    case ArmorString => Armor
    case SocksString => Socks
    case WeaponString => Weapon
    case GlovesString => Gloves
    case NecklaceString => Necklace
    case HelmetString => Helmet
  }

  val ItemTypeStrings: List[String] = List(
    BootsString,
    ArmorString,
    SocksString,
    WeaponString,
    GlovesString,
    NecklaceString,
    HelmetString
  )

}
