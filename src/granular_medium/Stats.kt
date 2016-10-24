package granular_medium

import granular_medium.models.State

class Stats(particleCount: Int, parameters: Parameters) {

    companion object {
        private val EQUILIBRIUM_ENERGY = 1E-8
        private val DELTA_T = .1
    }

    private var totalTime: Double = .0
    private val particleStream = mutableListOf<Double>()
    private val energyOverTime = mutableListOf<Double>()
    private var nextStep = DELTA_T
    private var timeToEquilibrium: Double? = null

    val stateList = mutableListOf<State>()

    private val parameters: Parameters
    public val particleCount: Int

    init {
        this.parameters = parameters
        this.particleCount = particleCount
    }

    fun update(state: State, deltaTime: Double) {
        totalTime += deltaTime
        if (totalTime >= nextStep) {
            stateList.add(state)
            nextStep += DELTA_T
            val kineticEnergy = state.calculateKineticEnergy()
            energyOverTime.add(kineticEnergy)
            if (timeToEquilibrium == null && kineticEnergy < EQUILIBRIUM_ENERGY) {
                timeToEquilibrium = totalTime
                println("Equilibrium reached after $timeToEquilibrium seconds")
            }
        }
        particleStream.addAll(
                state.particles
                    .filter { particle -> particle.oldPosition.y >= 0 && particle.position.y < 0 }
                    .map { totalTime }
        )
    }

    fun print() {
        println("${particleStream.size} particles were streamed in $totalTime for a ratio of "
                + "${particleStream.size / totalTime}/sec")
        print("The positions in time were: ")
        particleStream.forEach { time -> print("$time, ") }
        println()
//        println("The kinetic energy over time was:")
//        energyOverTime.forEach { entry -> println("$entry") }
        calculateAccumulatedStream().forEach { count ->
            print(", $count")
        }
    }

    fun equilibriumReached() = timeToEquilibrium != null
    fun getEnergyOverTime() = energyOverTime

    fun calculateAccumulatedStream(): DoubleArray {
        val totalFrames = (totalTime / DELTA_T).toInt()
        val particlesPerTimeFrame = DoubleArray(totalFrames)
        particleStream.forEach { time ->
            val timeIndex = (time / DELTA_T).toInt()
            (timeIndex..totalFrames - 1).forEach { index -> particlesPerTimeFrame[index]++ }
        }
        return particlesPerTimeFrame
    }

    fun getParticleStream() = particleStream
    fun getParameters() = parameters
}
