val gameConfiguration = mapOf("red" to 12, "green" to 13, "blue" to 14)

data class Set(val numberOfDice: Int, val colorOfDice: String)

fun main() {
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(readInput("Day02_test")) == 2286)
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

fun isSetPossible(set: String): Boolean {
    val (numberOfCubes, cubeColor) = set.trim().split(" ")
    val colorGameConfiguration = gameConfiguration[cubeColor] ?: error("should not happen")
    val isSetPossible = numberOfCubes.toInt() <= colorGameConfiguration
    if (!isSetPossible) {
        println("$set is not possible")
    }
    return numberOfCubes.toInt() <= colorGameConfiguration
}

fun isGamePossible(row: String): Boolean {
    val gameId = row.first { it.isDigit() }.digitToInt()
    val sets = row.substringAfter(":")
        .split(";")
        .toSet()
        .map { it.split(",") }.flatten()
    val gameIsPossible = sets.all { isSetPossible(it) }
    if (!gameIsPossible) {
        println("Game Id:$gameId is not possible")
    }
    return sets.all { isSetPossible(it) }
}

fun powerOfSetOfCubes(row: String): Int {
    val power = row.substringAfter(":")
        .split(";")
        .asSequence()
        .map { it.split(",") }
        .flatten()
        .map { it.trim() }
        .map {
            Set(it.substringBefore(" ").toInt(), it.substringAfter(" "))
        }
        .groupBy { it.colorOfDice }
        .map { it.value.maxOf { set -> set.numberOfDice } }
        .reduce { product, element -> product * element }
    return power
}

fun part1(lines: List<String>): Any {
    val filteredLines = lines.filter { isGamePossible(it) }
    val sum = filteredLines.map { it.substringAfter("Game ").substringBefore(":") }.sumOf { it.toInt() }
    sum.println()
    return filteredLines.sumOf { line -> line.first() { it.isDigit() }.digitToInt() }
}

fun part2(lines: List<String>): Any {
    return lines.sumOf { powerOfSetOfCubes(it) }
}

