package model.progress

import specs.{FlatTestSpec, SerializableSpec}

class SerializableHistoryTest extends FlatTestSpec with SerializableSpec{

  var visitedNodesId: List[Int] = List(0,1,2)
  var undefinedVisitedNodesList: List[Int] = _
  var serializableHistory: SerializableHistory = SerializableHistory(visitedNodesId)

  it should "contain the visited nodes id list" in {
    serializableHistory.visitedNodesId shouldEqual visitedNodesId
  }

  it should "not contain a null visited nodes id list" in {
    intercept[IllegalArgumentException]{
      SerializableHistory(undefinedVisitedNodesList)
    }
  }

  it should "not contain an empty visited nodes id list" in {
    intercept[IllegalArgumentException]{
      SerializableHistory(List[Int]())
    }
  }

  it should "not contain a visited nodes id list with duplicate ids" in {
    intercept[IllegalArgumentException]{
      SerializableHistory(List[Int](0,1,0,2))
    }
  }

  it should behave like serializationTest(serializableHistory)

}
