import kotlin.math.abs

fun printPyramidRow(currentSequence: List<Int>, height: Int, prediction: Int, size: Int) {
    // Calculate the number of spaces needed before the numbers
    val spacesBefore = height - currentSequence.size

    // Print spaces before the numbers
    repeat(spacesBefore) {
        print("   ")
    }

    // Print the numbers in the current sequence
    currentSequence.forEachIndexed { index, num ->
        print("$num")
        if (index < currentSequence.size - 1) {
            print("   ") // Adjust spaces between numbers
        }
    }
    print(" size: $size")
    println() // Move to the next line after each row
}

fun writePyramidRowToFile(currentSequence: List<Int>, fileName: String, height: Int) {
    val writer = java.io.FileWriter(fileName)

    // Calculate the number of spaces needed before the numbers
    val spacesBefore = height - currentSequence.size

    // Write spaces before the numbers
    repeat(spacesBefore) {
        writer.write("   ")
    }

    // Write the numbers in the current sequence
    currentSequence.forEachIndexed { index, num ->
        writer.write("$num")
        if (index < currentSequence.size - 1) {
            writer.write("   ") // Adjust spaces between numbers
        }
    }

    writer.write(System.lineSeparator()) // Move to the next line after each row
    writer.close()
}

fun List<Int>.isNotTargetSequence(): Boolean {
    return this.any { it != 0 && this.size == 1 }
}

fun transformSequence(sequence: List<Int>): MutableList<MutableList<Int>> {
    val sequences = mutableListOf<MutableList<Int>>()
    var currentSequence = sequence
    sequences.add(sequence.toMutableList())
    while (currentSequence.any { it != 0 } && currentSequence.size != 1) {
        currentSequence = currentSequence
            .windowed(2, 1)
            .map {
                abs(it.first() - it.last())
            }.toMutableList()
        sequences.add(currentSequence)
    }
    return sequences
}

fun main() {
fun getSequences(sequence: List<Int>): MutableList<MutableList<Int>> {
    val sequences = mutableListOf<MutableList<Int>>()

    sequences.add(sequence.toMutableList())
    do {
        val currentSequence = sequences
            .last()
            .windowed(2, 1)
            .map {(a,b)->
                b-a
            }
        sequences.add(currentSequence.toMutableList())
    } while (currentSequence.any { it != 0 })
    return sequences
}
    fun makePredictionInFuture(sequences: MutableList<MutableList<Int>>): Int {


        for (currentSequence in sequences.lastIndex -1 downTo 0){
            val left=sequences[currentSequence].last()
            val below= sequences[currentSequence+1].last()
            sequences[currentSequence].add(left+below)
        }

        return sequences.first().last()
    }
    fun makePredictionInPast(sequences: MutableList<MutableList<Int>>): Int {
        for (currentSequence in sequences.lastIndex -1 downTo 0){
            val right=sequences[currentSequence].first()
            val below= sequences[currentSequence+1].first()
            sequences[currentSequence].add(0,right-below)
        }

        return sequences.first().first()
    }


    fun part1(histories: List<List<Int>>): Int {
        val res = histories.sumOf { makePredictionInFuture(getSequences(it)) }
        return res
    }

    fun part2(histories: List<List<Int>>): Int {
        val res=histories.sumOf { makePredictionInPast(getSequences(it)) }
         return res
    }


    val lines = readInput("Day09")
    val histories = lines
        .map { it.split(" ") }
        .map { (it.map { it.toInt() }) }
    //check(part1(histories) == 14746755+741+25120609)
   // check(part2(histories) == 2)


    part1(histories).println()

    part2(histories).println()

}


