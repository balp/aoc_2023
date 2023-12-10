import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String) = Path("resources/$name.txt").readLines()

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)