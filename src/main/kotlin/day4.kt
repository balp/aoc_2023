import kotlin.math.pow

data class Card(val name: String, val winningNumbers: Set<String>, val numbers: Set<String>, val wins: Set<String>, var count: Int)
fun main() {
    val fileName =
//        "sample_4_1"
        "4_1"
    val input = readInput(fileName)
    val lines = mutableListOf<Triple<Set<String>,Set<String>,Set<String>>>()
    val clines = mutableListOf<Card>()
    for (line in input) {
        line.println()
        val a = line.split(":")
        val card = a.first().trim()
        val b = a.last().split("|")
        val win = b.first().trim().split("\\s+".toRegex()).toSet()
        val num = b.last().trim().split("\\s+".toRegex()).toSet()
        val wins = num intersect win
        println(" '$card' '$win' '$num' '$wins'")
        lines.add(Triple(win, num, wins))
        clines.add(Card(card, win, num, wins, 1))
    }
    var sum = 0
    for (line in lines) {
        val (_, _, wins) = line
        if (wins.isNotEmpty()) {
            sum += ((2.0).pow(wins.size - 1)).toInt()
        }
    }
    println("Fist solution is $sum")
    var index = 0
    while (index < clines.size) {
        val line = clines[index]
        println("line have ${line.wins}")
        if (line.wins.isNotEmpty()) {
            for (n in index+1..index+line.wins.size) {
                println("add line: $n")
                clines[n].count += clines[index].count
            }
        }
        index += 1
    }
    println("Second list is $clines")
    println("Second lines ${clines.size}")
    var sum2 = 0
    for (c in clines) {
        sum2 += c.count
    }
    println("Second sum ${sum2}")
}