package day06

import println
import readInput

fun main() {
    Day06()
}

data class Race(
    val timeAllowed: Long,
    val recordDistance: Long
)

enum class Part { ONE, TWO }

class Day06 {
    init {
        val exampleInput = readInput("day06/Day06_Example")
        val exampleRaces = exampleInput.parse(Part.ONE)
        check(part1(exampleRaces) == 288)
        val exampleRace = exampleInput.parse(Part.TWO).single()
        check(part2(exampleRace) == 71503)

        val input = readInput("day06/Day06")
        val races = input.parse(Part.ONE)
        part1(races).println()
        val race = input.parse(Part.TWO).single()
        part2(race).println()
    }

    private fun List<String>.parse(part: Part): List<Race> {
        if (part == Part.ONE) {
            val timesAllowed = first()
                .removePrefix("Time:")
                .split(" ")
                .filterNot { element -> element.isBlank() }
                .map { time -> time.toLong() }
            val recordDistances = last()
                .removePrefix("Distance:")
                .split(" ")
                .filterNot { element -> element.isBlank() }
                .map { time -> time.toLong() }

            return (timesAllowed zip recordDistances).map { (timeAllowed, recordDistance) ->
                Race(
                    timeAllowed = timeAllowed,
                    recordDistance = recordDistance
                )
            }
        } else {
            return listOf(
                Race(
                    timeAllowed = first().filter { character -> character.isDigit() }.toLong(),
                    recordDistance = last().filter { character -> character.isDigit() }.toLong()
                )
            )
        }
    }

    private val Race.winningOptions: Int
        get() = (1..<timeAllowed).count { startingSpeed -> (timeAllowed - startingSpeed) * startingSpeed > recordDistance }

    private fun part1(races: List<Race>) = races.fold(1) { answer, race -> answer * race.winningOptions }

    private fun part2(race: Race) = race.winningOptions
}