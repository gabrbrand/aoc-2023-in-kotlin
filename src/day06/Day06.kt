package day06

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    Day06()
}

data class Race(
    val time: Long,
    val recordDistance: Long
)

enum class Part { ONE, TWO }

class Day06 {
    init {
        val exampleInput = Path("src/day06/Day06_Example.txt").readLines()
        val exampleRacesPart1 = exampleInput.parse(Part.ONE)
        check(part1(exampleRacesPart1) == 288)
        val exampleRacePart2 = exampleInput.parse(Part.TWO).single()
        check(part2(exampleRacePart2) == 71503)

        val input = Path("src/day06/Day06.txt").readLines()
        val racesPart1 = input.parse(Part.ONE)
        println(part1(racesPart1))
        val racePart2 = input.parse(Part.TWO).single()
        println(part2(racePart2))
    }

    private fun List<String>.parse(part: Part): List<Race> {
        val races = mutableListOf<Race>()
        if (part == Part.ONE){
            val times = first()
                .removePrefix("Time:")
                .split(" ")
                .filterNot { element -> element.isBlank() }
                .map { time -> time.toLong() }
            val distances = last()
                .removePrefix("Distance:")
                .split(" ")
                .filterNot { element -> element.isBlank() }
                .map { time -> time.toLong() }

            for (i in 0..times.lastIndex) {
                races.add(
                    Race(
                        time = times[i],
                        recordDistance = distances[i]
                    )
                )
            }
        } else {
            races.add(
                Race(
                    time = first().filter { element -> element.isDigit() }.toLong(),
                    recordDistance = last().filter { element -> element.isDigit() }.toLong()
                )
            )
        }

        return races
    }

    private val Race.winningOptions: Int
        get() = (1..<time).count { startingSpeed -> (time - startingSpeed) * startingSpeed > recordDistance }

    private fun part1(races: List<Race>) = races.fold(1) { answer, race -> answer * race.winningOptions }

    private fun part2(race: Race) = race.winningOptions
}