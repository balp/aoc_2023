fun main() {
    val fileName =
//        "sample_3_1"
        "3_1"
    val input = readInput(fileName)
    var partSums = 0
    var gearSums = 0

    var engineLayout = EngineLayout()
    for (line in input) {
        line.println()
        engineLayout.addLine(line)
    }
    for ((x, row) in engineLayout.rows.withIndex()) {
        val digits = row.digits()
        for ((value, positions) in digits) {
            println("Looking at $value with $positions")
            val neighbours = neighbours(x, positions)
            for (neighbour in neighbours) {
                val (nX, nY) = neighbour
                try {
                    val symbol = engineLayout.symbolAt(nX, nY)
                    if (symbol.type == EngineSymbolType.SYMBOL) {
                        println("Adding $value to $partSums")
                        partSums += value
                    }
                } catch (_: Exception) {
                }

            }
        }
    }
    for ((x, row) in engineLayout.rows.withIndex()) {
        val gearSymbols = row.gearSymbols()
        for (position in gearSymbols) {
            println("Looking at symbol x $x, y $position")
            val digits = engineLayout.digitsAround(x, position)
            digits.println()
            if (digits.size == 2) {
                println("is gear $x, $position")
                gearSums += digits.first().first * digits.last().first
            }
        }
    }
    println("Adding parts: $partSums")
    println("Adding gears: $gearSums")

}

fun neighbours(x: Int, positions: List<Int>): Set<Pair<Int, Int>> {
    var res = mutableSetOf<Pair<Int, Int>>()
    for (y in positions.first()-1..positions.last()+1) {
        res.add(Pair(x-1, y))
    }
    res.add(Pair(x, positions.first()-1))
    res.add(Pair(x, positions.last()+1))
    for (y in positions.first()-1..positions.last()+1) {
        res.add(Pair(x+1, y))
    }
    return res
}

enum class EngineSymbolType { NUMBER, SYMBOL, EMPTY }
data class EngineSymbol(val type: EngineSymbolType, val value: Char)
data class EngineRow(val data: List<EngineSymbol>)
fun EngineRow.digits(): List<Pair<Int, List<Int>>> {
    val digits = mutableListOf<Pair<Int, List<Int>>>()
    for ((i, pos) in data.withIndex()) {
        if (pos.type == EngineSymbolType.NUMBER) {
            if (i == 0 || data[i-1].type != EngineSymbolType.NUMBER) {
                var posistions = mutableListOf(i)
                var number = pos.value.toString()
                var count = i+1
                try {
                    while (data[count].type == EngineSymbolType.NUMBER) {
                        number += data[count].value
                        posistions.add(count)
                        count += 1
                    }
                } catch (_: IndexOutOfBoundsException) {}
                digits.add(Pair(number.toInt(), posistions))
            }
        }
    }
    return digits
}
fun EngineRow.gearSymbols(): List<Int> {
    val gears = mutableListOf<Int>()
    for ((i, pos) in data.withIndex()) {
        if (pos.type == EngineSymbolType.SYMBOL && pos.value == '*') {
            gears.add(i)
        }
    }
    return gears
}

class EngineLayout {
    var rows: MutableList<EngineRow> = mutableListOf<EngineRow>()
    fun addLine(line: String) {
        val res = mutableListOf<EngineSymbol>()
        for (char in line) {
            when {
                char.isDigit() -> res.add(EngineSymbol(EngineSymbolType.NUMBER, char))
                char == '.' -> res.add(EngineSymbol(EngineSymbolType.EMPTY, char))
                else -> res.add(EngineSymbol(EngineSymbolType.SYMBOL, char))
            }
        }
        rows.add(EngineRow(res))
    }
    fun symbolAt(x: Int, y: Int): EngineSymbol {
        val row = rows[x]
        return row.data[y]
    }
    fun digitsAround(x: Int, y: Int): List<Pair<Int, List<Int>>> {
        val digits = mutableListOf<Pair<Int, List<Int>>>()
        for (row in x-1..x+1) {
            val rowDigits = rows[row].digits()
            for (rowDigit in rowDigits) {
                for (pos in rowDigit.second) {
                    if (pos in y-1..y+1) {
                        digits.add(rowDigit)
                        break
                    }
                }
            }
        }
        return digits
    }
}
