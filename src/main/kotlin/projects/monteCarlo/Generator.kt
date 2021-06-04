package projects.monteCarlo

import java.util.*
import kotlin.math.ln
import kotlin.math.sqrt

interface GeneratorProtocol {
    val generator: Random
    fun nextDouble(): Double
    fun nextInt(): Int

}
abstract class Generator(
        override val generator: Random = Random(seedGenerator.nextLong())
) : GeneratorProtocol {

    override fun nextInt(): Int {
        return nextDouble().toInt()
    }
}

class ExponentialGenerator(
        private val lambda: Double
) : Generator() {
    override fun nextDouble(): Double {
        return ln(1 - generator.nextDouble()) /(-lambda)
    }
}

class TriangularGenerator(
        private val from: Double,
        private val to: Double,
        private val mean: Double
) : Generator() {

    override fun nextDouble(): Double {
        val fc = (mean - from) / (to - from)
        val random = generator.nextDouble()
        return if( random < fc) {
            from + sqrt(random * (to - from) * (mean - from))
        } else {
            to - sqrt((1 - random) * (to - from) * (to - mean))
        }
    }
}

class RangedGenerator(
        private val from: Double,
        private val to: Double
) : Generator() {
    override fun nextDouble(): Double {
        return from + (to - from) * generator.nextDouble()
    }

    override fun nextInt(): Int {
        return nextDouble().toInt()
    }
}

var seedGenerator = Random()

