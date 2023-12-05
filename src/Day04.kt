import kotlin.math.pow

data class Card(
    val number: Int,
    val matchingNumbers: List<Int>,
    var isProcessed: Boolean = false,
    var amount: Int = 1
)

fun main() {
    val input = readInput("Day04")
    fun parseCards(line: String): Card {
        val cardNumber = line.substringAfter("Card ").substringBefore(":").trim().toInt()
        val (winningNumberString, myNumberString) = line.substringAfter(":")
            .split("|")
        val winningNumbers = winningNumberString.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val myNumbers = myNumberString.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val matchingNumbers = myNumbers.filter { winningNumbers.contains(it) }
        return Card(cardNumber, matchingNumbers)


    }

    fun part1(lines: List<String>): Int {
        val cards: List<Card> = lines.map { parseCards(it) }
        var totalPoints = 0
        for (card in cards) {
            val exponent: Double = card.matchingNumbers.count() - 1.toDouble()
            val points = 2.0.pow(exponent).toInt()
            totalPoints += points
        }
        return totalPoints
    }

    fun List<Card>.getNumberOfCards(list:List<Card>){
        for (card in this) {
            val numberOfNewCards = card.matchingNumbers.count()
            val newCardIndexRange = card.number + 1..card.number + numberOfNewCards
            val newCards = list.filter { it.number in newCardIndexRange }
            newCards.forEach { it.amount++ }
            newCards.getNumberOfCards(list)

        }

    }
    fun part2(lines: List<String>): Int {
        val cards = lines.map { parseCards(it) }
        cards.getNumberOfCards(cards)

        return cards.map { it.amount }.sumOf { it }
    }


    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)
    part1(input).println()
    part2(input).println()


}