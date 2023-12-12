import kotlin.math.max
import kotlin.math.min

data class Node(val value: String)

fun gcd(a: Long, b: Long): Long {
    if (a == 0L) {
        return b
    } else if (b == 0L) {
        return a
    } else {
        val min = min(a, b)
        val max = max(a, b)
        return gcd(min, max.mod(min))
    }
}

fun lcm(loopLengths: List<Int>): Long {
    val lcm = loopLengths
        .map { it.toLong() }
        .reduce { acc, bigInteger ->
            acc * bigInteger / gcd(acc, bigInteger)
        }
    return lcm
}

fun main() {
    fun part1(nodes: Map<Node, Pair<Node, Node>>, instructions: List<String>): Int {
        var currentNode = Node("AAA")
        var steps = 0
        while (currentNode.value != "ZZZ") {
            for (instruction in instructions) {
                val (left, right) = nodes[currentNode]!!
                when (instruction) {
                    "L" -> currentNode = left
                    "R" -> currentNode = right
                }
                steps++
                if (currentNode.value == "ZZZ") break
            }
        }

        return steps
    }

    fun steps(node: Node, nodes: Map<Node, Pair<Node, Node>>, instructions: List<String>): Int {
        var steps = 0
        var currentNode = node
        while (true) {
            for (instruction in instructions) {
                val (left, right) = nodes[currentNode]!!

                currentNode = when (instruction) {
                    "L" -> left
                    else -> right
                }
                steps++
                if (currentNode.value.endsWith("Z")) return steps
            }


        }
    }


    fun part2(nodes: Map<Node, Pair<Node, Node>>, instructions: List<String>): Long {
        val currentNodes = nodes
            .filterKeys { node -> node.value.endsWith("A") }.toList()
        val stepList = currentNodes
            .map { steps(it.first, nodes, instructions) }
        return lcm(stepList)
    }

    fun parseNavigationInstructions(lines: List<String>): List<String> {
        val instructions = lines
            .first()
            .split("")
            .map { it }
            .filter { it.isNotEmpty() }

        return instructions
    }

    fun parseNodes(lines: List<String>): Map<Node, Pair<Node, Node>> {
        val nodeList = lines
            .associate {
                val value = it.substringBefore(" = ")
                val (left, right) = it
                    .substringAfter(" = ")
                    .substringAfter("(")
                    .substringBefore(")")
                    .split(", ")
                Node(value) to (Node(left) to Node(right))
            }
        return nodeList
    }

    var lines = readInput("Day08")
    val instructions = parseNavigationInstructions(lines)
    lines = lines.drop(2)
    val nodes = parseNodes(lines)
    part1(nodes, instructions).println()
    part2(nodes, instructions).println()
}