fun main() {
    data class Game(val time: Int, val recordDistance: Int)

    fun part1(lines: List<String>): Int {
        var result = 1
        val gameTimes = lines.first().substringAfter("Time:").split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val distances = lines[1].substringAfter("Distance:").split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val games = (gameTimes zip distances).map {
            val (time, distance) = it
            Game(time, distance)
        }.map { game ->
            val list = mutableListOf<Int>()
            for (buttonHoldTime in 1..game.time) {
                list.add(buttonHoldTime * (game.time - buttonHoldTime))
            }
            list.filter { it > game.recordDistance }.count()
        }
        games.forEach {
            result *= it
        }
        return result
    }

    fun part2(lines: List<String>): Int {
        var result = 1L
        val gameTime =
            lines.first().substringAfter("Time:").trim().split("").filter { it.isNotBlank() }.joinToString("").toLong()
        val distance =
            lines[1].substringAfter("Distance:").split("").filter { it.isNotBlank() }.joinToString("").toLong()
        var list = mutableListOf<Long>()
        for (buttonHoldTime in 1..gameTime) {
            list.add(buttonHoldTime * (gameTime - buttonHoldTime))
        }
        list = list.filter { it > distance }.toMutableList()

        return list.count()
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}