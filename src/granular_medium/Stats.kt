package granular_medium

import granular_medium.models.State

class Stats {

    private var totalTime: Double = .0
    private var totalStream: Double = .0
    private val particleStream = mutableListOf<Double>()
    private val energyOverTime = sortedMapOf<Double, Double>()

    fun update(state: State, deltaTime: Double, isStep: Boolean) {
        totalTime += deltaTime
        totalStream += state.particles
                .filter { particle -> particle.oldPosition.y >= 0 && particle.position.y < 0 }
                .map { particleStream.add(totalTime) }
                .count()
        if (isStep) {
            energyOverTime.put(totalTime, state.calculateKineticEnergy())
        }
    }

    fun print() {
        println("$totalStream particles were streamed in $totalTime for a ratio of ${totalStream / totalTime}/sec")
        print("The positions in time were: ")
        particleStream.forEach { time -> print("$time, ") }
        println()
//        println("The kinetic energy over time was:")
//        energyOverTime.forEach { entry -> println("${entry.key} \t ${entry.value}") }
    }

    fun getEnergyOverTime() = energyOverTime

}
