package com.goldblastgames.themole

import scala.util.Random

object Role extends Enumeration {
  type Role = Value
  val AmericanMole, RussianMole, American, Russian = Value
}
import Role._

object Generator {
  val port = 2552
  val playerCount = 6
  val playerNames = Seq(
      "kane",
      "robert",
      "franklin",
      "aaron",
      "clayton",
      "daisy"
  )
  val skills = Seq(
      "wetwork",
      "information-gathering",
      "sexitude",
      "stoicism",
      "sabotage",
      "subterfuge"
  )
  val roles = Seq(
      ("America", "USSR"),
      ("USSR", "America"),
      ("America", "America"),
      ("America", "America"),
      ("USSR", "USSR"),
      ("USSR", "USSR")
  )
  val dropFreq = 14400000
  val missionFreq = 86400000

  def randomMax: Int = (Random.nextGaussian * 2.0 + 6.0).toInt

  def skill(name: String) = <skill name={ name } min="0" max={ randomMax.toString } />

  def player(name: String, camp: String, allegiance: String) = {
    <player name={ name } camp={ camp } allegiance={ allegiance }>
      <skills>
        {
          skills.map(name => skill(name))
        }
      </skills>
    </player>
  }

  def main(args: Array[String]) {
    val players = playerNames.zip(Random.shuffle(roles))

    val xml = <game>
      <port>{ port }</port>
      <players>
        {
          players.map { case (name, (camp, allegiance)) => player(name, camp, allegiance) }
        }
      </players>
      <session>
        <dropFreq>{ dropFreq }</dropFreq>
        <missionFreq>{ missionFreq }</missionFreq>
      </session>
    </game>

    println("Generating game.xml file...")
    println(xml)
    println("Saving game.xml file to current directory...")

    scala.xml.XML.save("game.xml", xml)
  }
}
