package net.voldrich.aoc21

// link to task
fun main() {
    Day12().run()
}

class Day12 : BaseDay() {
    override fun task1() : Int {
        val uniquePaths = CaveGraph(false).getUniquePaths()
        //uniquePaths.forEach { path -> println(path) }
        return uniquePaths.size
    }

    override fun task2() : Int {
        val uniquePaths = CaveGraph(true).getUniquePaths()
        //uniquePaths.forEach { path -> println(path) }
        return uniquePaths.size
    }

    inner class CaveGraph(val oneSmallCaveTwice : Boolean) {

        private val nodeMap = HashMap<String, CaveNode>()

        init {
            input.lines().forEach{ line ->
                val split = line.split("-")
                val startNode = getOrCreateNode(split[0])
                val endNode = getOrCreateNode(split[1])

                startNode.nextNodes.add(endNode)
                endNode.nextNodes.add(startNode)
            }
        }

        private fun getOrCreateNode(name: String): CaveNode {
            return nodeMap.computeIfAbsent(name) { CaveNode(it) }
        }

        fun getUniquePaths(): List<CavePath> {
            val startNode = nodeMap["start"]!!// ?: Exception("Start node not found")
            return startNode.nextNodes.flatMap { findPath(it, CavePath(startNode)) }.toList()
        }

        private fun findPath(node: CaveNode, nodePath: CavePath) : List<CavePath> {
            if (node.name == "end") {
                return listOf(CavePath(nodePath, node))
            }

            if (node.name == "start") {
                return listOf()
            }
            if (!node.isBigCave) {
                if (oneSmallCaveTwice) {
                    val groupBy = nodePath.nodes.filter { !it.isBigCave }.groupBy { it.name }

                    if (groupBy.values.maxOf { it.size } == 2) {
                        if (groupBy.contains(node.name)) {
                            return listOf()
                        }
                    }

                } else {
                    if (nodePath.nodes.contains(node)) {
                        return listOf()
                    }
                }
            }

            return node.nextNodes.flatMap { nextNode -> findPath(nextNode, CavePath(nodePath, node)) }
        }
    }

    class CavePath {
        val nodes: List<CaveNode>
        constructor(node: CaveNode) {
            nodes = listOf(node)
        }

        constructor(path: CavePath, node: CaveNode) {
            val pathCopy = path.nodes.toMutableList()
            pathCopy.add(node)
            nodes = pathCopy.toList()
        }

        override fun toString(): String {
            return "CavePath(nodes=${nodes.joinToString { it.name }})"
        }
    }

    class CaveNode(val name: String) {
        val isBigCave: Boolean = name.uppercase() == name

        var nextNodes = ArrayList<CaveNode>()

    }
}


