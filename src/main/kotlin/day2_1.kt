enum class Color {RED, GREEN, BLUE}

data class Draw(val count: Int, val color: Color)
data class GameSet(val draws: Set<Draw>)
data class Game(val id: Int, val sets: List<GameSet>)
fun Game.isValidIn(red: Int, green: Int, blue: Int): Boolean {
    for (set in sets) {
        for (draw in set.draws) {
            when (draw.color) {
                Color.RED -> if (draw.count > red) return false
                Color.GREEN -> if (draw.count > green) return false
                Color.BLUE -> if (draw.count > blue) return false
            }
        }
    }
    return true
}

fun Game.power(): Int {
    var min_red = 0
    var min_green = 0
    var min_blue = 0
    for (set in sets) {
        for (draw in set.draws) {
            when (draw.color) {
                Color.RED -> if (draw.count > min_red) min_red = draw.count
                Color.GREEN -> if (draw.count > min_green) min_green = draw.count
                Color.BLUE -> if (draw.count > min_blue) min_blue = draw.count
            }
        }
    }
    val power = min_red * min_blue * min_green
    power.println()
    return power
}

fun main() {
    val gameLineRegex = """Game (\d+)""".toRegex()
    val setRegex = """(\d+) (\w+)""".toRegex()
    val fileName =
//            "sample_2_1"
            "2_1"
    val input = readInput(fileName)
    var sum = 0
    var powers = 0
    for (line in input) {
        line.println()
        val idPart = line.substringBefore(':')
        val (id) = gameLineRegex
                .matchEntire(idPart.trim())
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect game $idPart in line $line")
        val setsPart = line.substringAfter(':')
        val sets = mutableListOf<GameSet>()
        for (set in setsPart.split(';')) {
            val draws = mutableSetOf<Draw>()
            for (draw in set.trim().split(',')) {
                val (count, colorName) = setRegex
                        .matchEntire(draw.trim())
                        ?.destructured
                        ?: throw IllegalArgumentException("Incorrect draw $draw in line $line")
                val clr = Color.valueOf(colorName.uppercase())
                val newDraw = Draw(count.toInt(), clr)
                draws.add(newDraw)
            }
            sets.add(GameSet(draws))
        }
        val game = Game(id.toInt(), sets)
        game.println()
        if (game.isValidIn(12, 13, 14)) {
            sum += game.id
        }
        powers += game.power()
        sum.println()
        powers.println()
    }
    sum.println()
    powers.println()
}