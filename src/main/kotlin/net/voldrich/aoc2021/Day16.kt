package net.voldrich.aoc2021

import net.voldrich.BaseDay
import net.voldrich.binaryString
import kotlin.text.StringBuilder

// https://adventofcode.com/2021/day/16
fun main() {
    Day16().run()
}

class Day16 : BaseDay() {
    override fun task1() : Int {
        val decoder = BitsDecoder(input.lines()[0])
        val packet = decoder.readPacket()
        return packet.getVersionSum()
    }

    override fun task2() : Long {
        val decoder = BitsDecoder(input.lines()[0])
        val packet = decoder.readPacket()
        return packet.getRepresentedValue()
    }

    class BitsPacket(val version : Int, val typeId: Int) {
        var value = 0L
        var subpackets = ArrayList<BitsPacket>()

        fun isLiteral() : Boolean {
            return typeId == 4
        }

        fun getVersionSum(): Int {
            return version + subpackets.sumOf { it.getVersionSum() }
        }

        fun getRepresentedValue(): Long {
            return when (typeId) {
                0 -> subpackets.sumOf { it.getRepresentedValue() }
                1 -> subpackets.fold(1L) { prev, packet -> prev * packet.getRepresentedValue() }
                2 -> subpackets.minOf { it.getRepresentedValue() }
                3 -> subpackets.maxOf { it.getRepresentedValue() }
                4 -> value
                5 -> if (subpackets[0].getRepresentedValue() > subpackets[1].getRepresentedValue()) 1L else 0L
                6 -> if (subpackets[0].getRepresentedValue() < subpackets[1].getRepresentedValue()) 1L else 0L
                7 -> if (subpackets[0].getRepresentedValue() == subpackets[1].getRepresentedValue()) 1L else 0L
                else -> throw Exception("Unknown operator $typeId")
            }
        }
    }

    class BitsDecoder(transmissionStr: String) {
        val binary = transmissionStr.binaryString()
        var pos = 0

        fun readPacket() : BitsPacket {
            val version = readVersion()
            val typeId = readTypeId()

            val packet = BitsPacket(version, typeId)
            if (packet.isLiteral()) {
                packet.value = readLiteral()
            } else {
                readOperator(packet)
            }

            return packet
        }

        fun readVersion() = read(3).toInt(2)

        fun readTypeId() = read(3).toInt(2)

        fun readLiteral() : Long {
            val sb = StringBuilder()
            while (read(1) == "1") {
                sb.append(read(4))
            }
            sb.append(read(4))

            return sb.toString().toLong(2)
        }

        fun readOperator(packet: BitsPacket) {
            val lengthTypeId = read(1)
            if (lengthTypeId == "0") {
                val length = read(15).toInt(2)
                val posEnd = pos + length
                while (pos < posEnd) {
                    packet.subpackets.add(readPacket())
                }
            } else if (lengthTypeId == "1") {
                val packetNum = read(11).toInt(2)
                for (i in 1 .. packetNum) {
                    packet.subpackets.add(readPacket())
                }
            }
        }

        fun read(size: Int): String {
            val ret = binary.substring(pos, pos + size)
            pos += size
            return ret
        }
    }
}
