package net.voldrich.aoc2022

import net.voldrich.BaseDay
import java.lang.RuntimeException

// https://adventofcode.com/2022/day/7
fun main() {
    Day07().run()
}

class Day07 : BaseDay() {

    override fun task1() : Int {
        val rootDir = parseDirectoryTree()
        return rootDir.filterDirectories { it.dirSize <= 100000 }.sumOf { it.dirSize }
    }

    override fun task2() : Int {
        val hddSize = 70000000
        val freeSpaceNeeded = 30000000
        val rootDir = parseDirectoryTree()
        val unusedSpace = hddSize - rootDir.dirSize
        val minimumDirSizeToDelete = freeSpaceNeeded - unusedSpace
        return rootDir.filterDirectories { it.dirSize > minimumDirSizeToDelete }.minOf { it.dirSize }
    }

    private fun parseDirectoryTree(): ElfDir {
        val rootDir = ElfDir("/")
        var currentDir = rootDir
        val cmds = input.lines()
        var num = 0
        while (num < cmds.size) {
            val cmd = cmds[num]
            if (cmd.startsWith("$ cd")) {
                val dirName = cmd.substring(5)
                if (dirName == "/") {
                    currentDir = rootDir
                } else if (dirName == "..") {
                    currentDir = currentDir.parent ?: throw RuntimeException("Cannot go up from root")
                } else {
                    currentDir = currentDir.createSubDir(dirName)
                }
                num++
            } else if (cmd == "$ ls") {
                num++
                while (num < cmds.size && !cmds[num].startsWith("$")) {
                    val line = cmds[num]
                    if (line.startsWith("dir")) {
                        val dirName = line.substring(3).trim()
                        currentDir.createSubDir(dirName)
                    } else {
                        val split = line.split(" ")
                        currentDir.createFile(split[1], split[0].toInt())
                    }
                    num++
                }
            }
        }
        rootDir.calculateDirectorySize()
        return rootDir
    }
}

class ElfDir(val name: String, val parent: ElfDir? = null) {

    val subDirs: MutableMap<String, ElfDir> = mutableMapOf()
    val files: MutableMap<String, ElfFile> = mutableMapOf()
    var dirSize = 0

    fun createSubDir(dirName: String): ElfDir {
        return subDirs.computeIfAbsent(dirName) { ElfDir(dirName, this) }
    }

    fun createFile(name: String, size: Int) {
        files.computeIfAbsent(name) { ElfFile(name, size) }
    }

    fun calculateDirectorySize(): Int {
        dirSize = files.values.sumOf { it.size } + subDirs.values.sumOf { it.calculateDirectorySize() }
        return dirSize
    }

    fun filterDirectories(predicate: (ElfDir) -> Boolean): List<ElfDir> {
        if (predicate.invoke(this)) {
            return listOf(this) + subDirs.values.flatMap { it.filterDirectories(predicate) }
        } else {
            return subDirs.values.flatMap { it.filterDirectories(predicate) }
        }
    }
}

data class ElfFile(val name: String, val size: Int)

