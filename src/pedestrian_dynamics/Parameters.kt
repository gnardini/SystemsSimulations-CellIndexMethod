package pedestrian_dynamics

class Parameters(
        val id: Int,
        val particleCount: Int,
        val targetSpeed: Double,
        val deltaTime: Double,
        val staticParticlesPerControl: IntArray,
        val delayPerControl: IntArray,
        val maxPeoplePerSection: IntArray) {
    val l = 50
    val w = 10
    val d = 6
    val minParticleRadius = 0.5 / 2
    val maxParticleRadius = 0.58 / 2
    val mass = 75
    val a = 2000.0
    val b = 0.08
    val kn = 1.2e5
    val kt = 2 * 2.4e5
    val tao = -.5
}
