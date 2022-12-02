package net.voldrich.aoc2021

import net.voldrich.BaseDay
import org.jheaps.AddressableHeap
import org.jheaps.tree.FibonacciHeap
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.system.measureTimeMillis


// https://adventofcode.com/2021/day/15
fun main() {
    Day15().run()
}

class Day15 : BaseDay() {
    override fun task1() : Int {
        val graph = Graph()

        graph.calculateShortestPath()

        return graph.getEndNode().getTotalDistance()
    }

    override fun task2() : Int {
        val graph = Graph()
        val graphExpanded = Graph(graph, 5)

        graphExpanded.calculateShortestPathOptimized()

        return graphExpanded.getEndNode().getTotalDistance()
    }

    fun performanceTest(iterationCount : Int) {
        val graph = Graph()

        val graphExpanded = Graph(graph, 5)

        val averageNonOptimized = (0..iterationCount).map {
            graphExpanded.reset()
            graphExpanded.calculateShortestPath()
        }.average()

        val averageOptimized = (0..iterationCount).map {
            graphExpanded.reset()
            graphExpanded.calculateShortestPathOptimized()
        }.average()
        println("Average time non optimized: $averageNonOptimized")
        println("Average time optimized: $averageOptimized")
    }

    inner class Graph {
        private val nodes : List<List<Node>>

        constructor(graph: Graph, expand: Int) {
            val sourceSizeY = graph.nodes.size
            val sourceSizeX = graph.nodes[0].size
            val expandedNodes = ArrayList<List<Node>>(sourceSizeY * expand)
            for (y in 0 until sourceSizeY * expand) {
                val row = ArrayList<Node>(sourceSizeX * expand)

                for (x in 0 until sourceSizeX * expand) {
                    if (y < sourceSizeY && x < sourceSizeX) {
                        row.add(graph.nodes[y][x].clone(y, x, 0))
                    } else {
                        row.add(graph.nodes[y % sourceSizeY][x % sourceSizeX].clone(y, x,(y / sourceSizeY) + (x / sourceSizeX)))
                    }
                }
                expandedNodes.add(row)
            }

            nodes = expandedNodes

            println("Total nodes: ${nodes.size * nodes[0].size}")
        }

        constructor() {
            nodes = input.lines().mapIndexed { y, line ->
                line.toCharArray().mapIndexed { x, char -> Node(y, x, char.digitToInt()) }
            }
            println("Total nodes: ${nodes.size * nodes[0].size}")
        }

        // Dijkstra algorithm implementation
        fun calculateShortestPath(): Long {
            return measureTimeMillis {
                val startNode = nodes[0][0]
                startNode.distance = 0
                val settledNodes = HashSet<Node>()
                val unsettledNodes = PriorityQueue<Node> { node1, node2 -> node1.distance - node2.distance }

                unsettledNodes.add(startNode)
                while (unsettledNodes.isNotEmpty()) {
                    val currentNode: Node = unsettledNodes.remove()
                    for (nextNode in getAdjacentNodes(currentNode)) {
                        if (!settledNodes.contains(nextNode)) {
                            nextNode.considerNode(currentNode)
                            if (!unsettledNodes.contains(nextNode)) {
                                unsettledNodes.add(nextNode)
                            }
                        }
                    }
                    settledNodes.add(currentNode)
                }
            }
        }

        // Dijkstra algorithm implementation using Fibonacci heap
        fun calculateShortestPathOptimized(): Long {
            return measureTimeMillis {
                val startNode = nodes[0][0]
                startNode.distance = 0
                val settledNodes = HashSet<Node>()

                val heap = FibonacciHeap<Node, Unit> { node1, node2 -> node1.distance - node2.distance }
                heap.insert(startNode)
                while (!heap.isEmpty) {
                    val currentNode = heap.deleteMin().key
                    for (nextNode in getAdjacentNodes(currentNode)) {
                        if (!settledNodes.contains(nextNode)) {
                            if (nextNode.considerNode(currentNode)) {
                                nextNode.addOrDecreaseHeap(heap)
                            }
                        }
                    }
                    settledNodes.add(currentNode)
                }
            }
        }

        private val adjacentNodeIndexes = listOf(Pair(0, -1), Pair(0, 1), Pair(1, 0), Pair(-1, 0))

        private fun getAdjacentNodes(node: Node): List<Node> {
            return adjacentNodeIndexes.mapNotNull {
                if (node.posy + it.first >= 0 && node.posy + it.first < nodes.size) {
                    if (node.posx + it.second >= 0 && node.posx + it.second < nodes[node.posy].size) {
                        return@mapNotNull nodes[node.posy + it.first][node.posx + it.second]
                    }
                }
                null
            }
        }

        fun reset() {
            nodes.forEach { it.forEach { node -> node.reset() } }
        }

        fun getEndNode(): Node {
            return nodes[nodes.size-1][nodes[0].size -1]
        }
    }

    class Node(val posy: Int, val posx: Int, val riskLevel: Int) {

        var previousNode : Node? = null

        var distance = Int.MAX_VALUE

        var handle : AddressableHeap.Handle<Node, Unit>? = null

        fun considerNode(prevNodeCandidate: Node) : Boolean {
            if (prevNodeCandidate.distance + riskLevel < distance) {
                previousNode = prevNodeCandidate
                distance = prevNodeCandidate.distance + riskLevel
                return true
            }
            return false
        }

        fun addOrDecreaseHeap(heap: FibonacciHeap<Node, Unit>) {
            if (handle == null) {
                handle = heap.insert(this)
            } else {
                handle!!.decreaseKey(this)
            }
        }

        fun getTotalDistance() : Int {
            if (previousNode != null) {
                return previousNode!!.getTotalDistance() + riskLevel
            }
            return 0
        }

        fun reset() {
            previousNode = null
            distance = Int.MAX_VALUE
            handle = null
        }

        override fun toString(): String {
            return "Node(posy=$posy, posx=$posx, riskLevel=$riskLevel, distance=$distance)"
        }

        fun clone(posy: Int, posx: Int, valueIncrease: Int): Node {
            var newRiskLevel = riskLevel + valueIncrease
            if (newRiskLevel > 9) {
                newRiskLevel -= 9
            }
            return Node(posy, posx, newRiskLevel)
        }


    }


}
