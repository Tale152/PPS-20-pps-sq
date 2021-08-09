package view.util

object InputUtility {
  /**
   * Does check the input to prevent side effects
   *
   * @return the chosen pathway
   */
   def inputAsInt(inputValueStrategy: () => String, isNotValidStrategy: String => Boolean): Int = {
    var input: String = ""
    do {
      input = inputValueStrategy()
      if(isNotValidStrategy(input)){
        println("Please insert a valid value.")
      }
    } while (isNotValidStrategy(input))
    input.toInt - 1
  }
}