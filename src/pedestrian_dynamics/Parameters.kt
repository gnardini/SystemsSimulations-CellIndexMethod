package pedestrian_dynamics

class Parameters(val particleCount: Int, val targetSpeed: Double, val deltaTime: Double) {
    val l = 20
    val w = 20
    val d = 1.2
    val minParticleRadius = 0.25
    val maxParticleRadius = 0.29
    val mass = 75
    val a = 2000.0
    val b = 0.08
    val kn = 1.2e5
    val kt = 2.4e5
    val tao = -.5
}
