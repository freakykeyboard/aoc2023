
fun main() {
    val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            var firstDigit = ""
            var lastDigit = ""
            for (char in line) {
                if (char.isDigit()) {
                    if (firstDigit == "") {
                        firstDigit = char.toString()
                        lastDigit = char.toString()
                    } else {
                        lastDigit = char.toString()
                    }
                }
            }
            sum += (firstDigit + lastDigit).toInt()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            var firstDigit = ""
            var lastDigit = ""
            var words = ""
            for (char in line) {
                if (char.isDigit()) {

                    if (firstDigit == "") {
                        firstDigit = char.toString()
                        lastDigit = char.toString()
                    } else {
                        lastDigit = char.toString()
                    }
                } else {
                    words += char.toString()
                    val result = words.findAnyOf(digits)
                    if (result != null) {
                        val digit = when (result.second) {
                            "one" -> "1"
                            "two" -> "2"
                            "three" -> "3"
                            "four" -> "4"
                            "five" -> "5"
                            "six" -> "6"
                            "seven" -> "7"
                            "eight" -> "8"
                            "nine" -> "9"
                            else -> null
                        }
                        if (digit != null) {
                            if (firstDigit == "") {
                                firstDigit = digit
                                lastDigit = digit
                            } else {
                                lastDigit = digit
                            }
                        }
                        words = words.last().toString()
                    }
                }
            }
            sum += (firstDigit + lastDigit).toInt()
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
