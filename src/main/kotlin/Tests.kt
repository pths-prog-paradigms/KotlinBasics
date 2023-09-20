import java.io.File
import java.util.Random
import kotlin.math.roundToLong

fun main() = TestsUtil().run {
    testBlock("Basic") {
        testGroup("Manual") {
            compare(::correctSum, ::sum, listOf(), "sum")
            compare(::correctSum, ::sum, listOf(listOf(1, 2, 3)), "sum")
            compare(::correctSum, ::sum, listOf(listOf(1), listOf(2), listOf(3)), "sum")
            compare(::correctSum, ::sum, listOf(listOf(), listOf(), listOf()), "sum")
            compare(::correctSum, ::sum, listOf(listOf(), listOf(1), listOf(1, 2)), "sum")
            compare(::correctSum, ::sum, listOf(listOf(2, 3, 4), listOf(1), listOf(1, 2)), "sum")
        }
        val seed = 5663023956630239L
        val rnd = Random(seed)
        testGroup("Random light") {
            repeat(100) {
                val input = List(rnd.nextInt(5)) { List(rnd.nextInt(5)) { rnd.nextInt(-10, 10) } }
                compare(::correctSum, ::sum, input, "sum")
            }
        }
        testGroup("Random heavy", listOf("Manual", "Random light")) {
            repeat(100) {
                val input = List(rnd.nextInt(100)) { List(rnd.nextInt(100)) { rnd.nextInt() } }
                compare(::correctSum, ::sum, input, "sum", silentArgs = true, silentRes = true)
            }
        }
    }
    testBlock("Advanced") {
        Unit
    }
    testBlock("Bonus") {
        Unit
    }

    println()
    println("Results: ")
    fun res(key: String): Double {
        val passed = results.filterKeys { it.first == key }.values.sum()
        val of = total.filterKeys { it.first == key }.values.sum()
        return if (of == 0) 1.0 else passed * 1.0 / of
    }


    indent++
    println("Basic   : ${((res("Basic") * 1000).roundToLong() / 10)}%")
    println("Advanced: ${((res("Advanced") * 1000).roundToLong() / 10)}%")
    println("Bonus   : ${((res("Bonus") * 1000).roundToLong() / 10)}%")
    indent--
    val file = File("res.txt")
    if (file.exists()) file.delete()
    file.createNewFile()
    file.writeText(
        "Basic   : ${((res("Basic") * 1000).roundToLong() / 10)}%" + "\n" +
                "Advanced: ${((res("Advanced") * 1000).roundToLong() / 10)}%" + "\n" +
                "Bonus   : ${((res("Bonus") * 1000).roundToLong() / 10)}%" + "\n"
    )

}
