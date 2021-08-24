package controller.util.serialization

import controller.util.ResourceName
import controller.util.serialization.StoryNodeSerializer.{deserializeStory, serializeStory}
import model.nodes.{Pathway, StoryNode}
import specs.FlatTestSpec
import util.TestUtil.deleteFolder

import java.io.{File, FileNotFoundException}

class StoryNodeSerializerTest extends FlatTestSpec {
  private val serializationFileNameTest =  "serializationTest"
  private val serializationPathTest: String = ResourceName.TempDirectory + "/" + serializationFileNameTest
  private val serializationFile = new File(serializationPathTest)

  val destinationNodeCondition: StoryNode = StoryNode(2, "narrative", Set.empty)
  val destinationNodeNoCondition: StoryNode = StoryNode(1, "narrative", Set.empty)
  val pathwayNoCondition: Pathway = Pathway("description", destinationNodeNoCondition, None)
  val pathwayCondition: Pathway = Pathway("description", destinationNodeCondition, Some(_ => true))
  val startingNode: StoryNode = StoryNode(0, "narrative", Set(pathwayNoCondition, pathwayCondition))

  "The serialization of the StoryNode" should "succeed" in {
    deleteFolder(serializationFile)
    serializeStory(startingNode, serializationPathTest)
    serializationFile.exists() shouldBe true
  }

  "The deserialization of the StoryNode" should "succeed" in {
    val storyNode: StoryNode = deserializeStory(serializationPathTest)
    storyNode.id shouldEqual 0
    storyNode.narrative shouldEqual "narrative"
    storyNode.pathways.exists(p => p.prerequisite.isEmpty && p.destinationNode.id == 1) shouldBe true
    storyNode.pathways.exists(p => p.prerequisite.isDefined && p.destinationNode.id == 2) shouldBe true
  }

  it should "throw a FileNotFoundException trying to deserialize a file that does not exists" in {
    deleteFolder(serializationFile)
    intercept[FileNotFoundException] {
      deserializeStory(serializationPathTest)
    }
  }

}
