package day01

import println
import readInput

fun main() {
    Day01()
}

class Day01 {
    private val digits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    init {
        val exampleInputPart1 = readInput("day01/Day01_Part1_Example")
        check(part1(exampleInputPart1) == 142)
        val exampleInputPart2 = readInput("day01/Day01_Part2_Example")
        check(part2(exampleInputPart2) == 281)

        val input = readInput("day01/Day01")
        part1(input).println()
        part2(input).println()
    }

    private fun String.calibrationValue(): Int {
        val firstDigit = first { char -> char.isDigit() }
        val lastDigit = last { char -> char.isDigit() }
        return "$firstDigit$lastDigit".toInt()
    }

    private fun part1(input: List<String>): Int {
        return input.sumOf { line -> line.calibrationValue() }
    }

    private fun Char.possibleDigits(isReversed: Boolean = false): List<String> {
        return if (isReversed) {
            digits.keys.map { digit -> digit.reversed() }.filter { digit -> digit.startsWith(this) }
        } else {
            digits.keys.filter { digit -> digit.startsWith(this) }
        }
    }

    private fun String.digitToInt() = digits[this] ?: -1

    private fun part2(input: List<String>): Int {
        val calibrationValues = mutableListOf<Int>()

        input.forEach { line ->
            var firstDigit = 0
            var currentIndex = 0
            while (firstDigit == 0 && currentIndex <= line.lastIndex) {
                val currentChar = line[currentIndex]
                if (currentChar.isDigit()) {
                    firstDigit = currentChar.digitToInt()
                } else {
                    val possibleDigits = currentChar.possibleDigits()
                    if (possibleDigits.isNotEmpty()) {
                        val maxLength = possibleDigits.maxOf { digit -> digit.length }
                        val endIndex = (currentIndex + maxLength).coerceAtMost(line.length)
                        val search = line.substring(currentIndex, endIndex)
                        possibleDigits.find { digit -> search.startsWith(digit) }?.let { digit ->
                            firstDigit = digit.digitToInt()
                        }
                    }
                }
                currentIndex++
            }

            var lastDigit = 0
            currentIndex = 0
            val reversedLine = line.reversed()
            while (lastDigit == 0 && currentIndex <= reversedLine.lastIndex) {
                val currentChar = reversedLine[currentIndex]
                if (currentChar.isDigit()) {
                    lastDigit = currentChar.digitToInt()
                } else {
                    val possibleDigits = currentChar.possibleDigits(isReversed = true)
                    if (possibleDigits.isNotEmpty()) {
                        val maxLength = possibleDigits.maxOf { digit -> digit.length }
                        val endIndex = (currentIndex + maxLength).coerceAtMost(reversedLine.length)
                        val search = reversedLine.substring(currentIndex, endIndex)
                        possibleDigits.find { digit -> search.startsWith(digit) }?.let { digit ->
                            lastDigit = digit.reversed().digitToInt()
                        }
                    }
                }
                currentIndex++
            }

            calibrationValues.add("$firstDigit$lastDigit".toInt())
        }

        return calibrationValues.sum()
    }
}