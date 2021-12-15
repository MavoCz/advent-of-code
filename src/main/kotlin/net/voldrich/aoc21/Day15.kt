package net.voldrich.aoc21

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


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

        graphExpanded.calculateShortestPath()

        return graphExpanded.getEndNode().getTotalDistance()
    }

    inner class Graph {
        val nodes : MutableList<MutableList<Node>>

        constructor(graph: Graph, expand: Int) {
            val sourceSizeY = graph.nodes.size
            val sourceSizeX = graph.nodes[0].size
            nodes = ArrayList(sourceSizeY * expand)
            for (y in 0 until sourceSizeY * expand) {
                val row = ArrayList<Node>(sourceSizeX * expand)

                for (x in 0 until sourceSizeX * expand) {
                    if (y < sourceSizeY && x < sourceSizeX) {
                        row.add(graph.nodes[y][x].clone(y, x, 0))
                    } else {
                        row.add(graph.nodes[y % sourceSizeY][x % sourceSizeX].clone(y, x,(y / sourceSizeY) + (x / sourceSizeX)))
                    }
                }
                nodes.add(row)
            }

            println("Total nodes: ${nodes.size * nodes[0].size}")
        }

        constructor() {
            nodes = input.lines().mapIndexed { y, line ->
                line.toCharArray().mapIndexed { x, char -> Node(y, x, char.digitToInt()) }.toMutableList()
            }.toMutableList()
            println("Total nodes: ${nodes.size * nodes[0].size}")
        }

        // Dijkstra algorithm implementation
        fun calculateShortestPath() {
            val startNode = nodes[0][0]
            startNode.distance = 0
            val settledNodes = HashSet<Node>()
            val unsettledNodes = PriorityQueue<Node>{ node1, node2 -> node1.distance - node2.distance }

            unsettledNodes.add(startNode)
            while (unsettledNodes.isNotEmpty()) {
                val currentNode: Node = unsettledNodes.remove()
                val nextNodes = getAdjacentNodes(currentNode)
                for (nextNode in nextNodes) {
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

        val adjacentNodeIndexes = listOf(Pair(0, -1), Pair(0, 1), Pair(1, 0), Pair(-1, 0))

        private fun getAdjacentNodes(node: Node): List<Node> {
            return adjacentNodeIndexes.mapNotNull {
                if (node.posy + it.first >= 0 && node.posy + it.first < nodes.size) {
                    if (node.posx + it.second >= 0 && node.posx + it.second < nodes[node.posy].size) {
                        nodes[node.posy + it.first][node.posx + it.second]
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
        }

        fun getEndNode(): Node {
            return nodes[nodes.size-1][nodes[0].size -1]
        }
    }

    class Node(val posy: Int, val posx: Int, val riskLevel: Int) {

        var previousNode : Node? = null

        var distance = Int.MAX_VALUE

        fun considerNode(prevNodeCandidate: Node) {
            if (prevNodeCandidate.distance + riskLevel < distance) {
                previousNode = prevNodeCandidate
                distance = prevNodeCandidate.distance + riskLevel
            }
        }

        fun getTotalDistance() : Int {
            if (previousNode != null) {
                return previousNode!!.getTotalDistance() + riskLevel
            }
            return 0
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
