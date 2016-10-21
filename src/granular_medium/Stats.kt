package granular_medium

import granular_medium.models.State

class Stats {

    companion object {
        private val EQUILIBRIUM_ENERGY = 1E-8
    }

    private var totalTime: Double = .0
    private var totalStream: Double = .0
    private val particleStream = mutableListOf<Double>()
    private val energyOverTime = sortedMapOf<Double, Double>()
    private var timeToEquilibrium: Double? = null

    fun update(state: State, deltaTime: Double, isStep: Boolean) {
        if (isStep && totalTime > 0) {
            val kineticEnergy = state.calculateKineticEnergy()
            energyOverTime.put(totalTime, kineticEnergy)
            if (timeToEquilibrium == null && kineticEnergy < EQUILIBRIUM_ENERGY) {
                timeToEquilibrium = totalTime
                println("Equilibrium reached after $timeToEquilibrium seconds")
            }
        }
        totalTime += deltaTime
        totalStream += state.particles
                .filter { particle -> particle.oldPosition.y >= 0 && particle.position.y < 0 }
                .map { particleStream.add(totalTime) }
                .count()
    }

    fun print() {
        println("$totalStream particles were streamed in $totalTime for a ratio of ${totalStream / totalTime}/sec")
        print("The positions in time were: ")
        particleStream.forEach { time -> print("$time, ") }
        println()
//        println("The kinetic energy over time was:")
//        energyOverTime.forEach { entry -> println("${entry.key} \t ${entry.value}") }
    }

    fun equilibriumReached() = timeToEquilibrium != null
    fun getEnergyOverTime() = energyOverTime

}
