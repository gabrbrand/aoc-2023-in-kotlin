package day02

import println
import readInput

fun main() {
    Day02()
}

data class Game(
    val id: Int,
    val sets: List<Set>
)

data class Set(
    var redCubes: Int = 0,
    var greenCubes: Int = 0,
    var blueCubes: Int = 0,
)

const val MAX_RED_CUBES = 12
const val MAX_GREEN_CUBES = 13
const val MAX_BLUE_CUBES = 14

class Day02 {
    init {
        val exampleInput = readInput("day02/Day02_Example")
        val exampleGames = parseInput(exampleInput)
        check(part1(exampleGames) == 8)
        check(part2(exampleGames) == 2286)

        val input = readInput("day02/Day02")
        val games = parseInput(input)
        part1(games).println()
        part2(games).println()
    }

    private fun parseInput(input: List<String>): List<Game> {
        val games = mutableListOf<Game>()
        input.forEach { game ->
            val gameData = game.split(":")

            val (_, id) = gameData.first().split(" ")
            val setsData = gameData.last().split(";")

            val sets = mutableListOf<Set>()
            setsData.forEach { setData ->
                val set = Set()
                setData.split(",").map { cubeData -> cubeData.trim() }.forEach { cubeData ->
                    val (count, color) = cubeData.split(" ")
                    when (color) {
                        "red" -> set.redCubes = count.toInt()
                        "green" -> set.greenCubes = count.toInt()
                        "blue" -> set.blueCubes = count.toInt()
                    }
                }
                sets.add(set)
            }

            games.add(
                Game(
                    id = id.toInt(),
                    sets = sets
                )
            )
        }

        return games
    }

    private fun Game.isPossible() =
        sets.all { set -> set.redCubes <= MAX_RED_CUBES && set.greenCubes <= MAX_GREEN_CUBES && set.blueCubes <= MAX_BLUE_CUBES }

    private fun part1(games: List<Game>): Int {
        val possibleGames = games.filter { game -> game.isPossible() }
        return possibleGames.sumOf { game -> game.id }
    }

    private fun List<Set>.power() = maxOf { set -> set.redCubes } * maxOf { set -> set.greenCubes } * maxOf { set -> set.blueCubes }

    private fun part2(games: List<Game>) = games.sumOf { game -> game.sets.power() }
}