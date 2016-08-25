package exercises;

import models.Particle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise3 extends Exercise {
	private static final int L = 20;
	private static final int interactionRadius = 1;
	private static final double radius = 0.25;

	public static void main(String[] args) {
		exercise3();
	}

	private static void exercise3() {
    Map<Integer, List<Long>> results = new HashMap<>();
    Integer runs = 13;

    Integer particleCount = 500;
    Integer M = 1;
    Integer MDelta = 1;

    List<Particle> particleList;
    for (int n = 0; n < 100; n++) {
      for (int i = 0; i < runs; i++) {
        Integer currentM = M + MDelta * i;
        Integer currentParticleCount = particleCount;
        particleList = getParticlesFromNewRandomInput(RANDOM_INPUT_PATH, currentParticleCount, currentM, radius);

        Long start = System.currentTimeMillis();
        cellIndexMethodWithEdge(particleList, currentM, L, 0, interactionRadius);
        Long end = System.currentTimeMillis();
        Long cellIndexMethodDuration = end - start;

        if (!results.containsKey(currentM)) {
          results.put(currentM, new ArrayList<Long>());
        }
        results.get(currentM).add(cellIndexMethodDuration);
      }
    }
    System.out.println("Particles: " + particleCount);
    for (Integer i = 1; i <= runs; i++) {
    	System.out.println(String.format("%.02f", mean(results.get(i), average(results.get(i)))));
//      System.out.println("M: " + i 
//    		  + " Primedio: " + average(results.get(i)) 
//    		  + " Varianza: " + mean(results.get(i), average(results.get(i))));
    }
  }

	private static double average(List<Long> list) {
		long sum = 0;
		for (Long l : list) {
			sum += l;
		}
		return Double.valueOf(sum) / list.size();
	}

	private static double mean(List<Long> list, double average) {
		double sum = 0;
		for (Long l : list) {
			sum += Math.pow(average - l, 2);
		}
		return Math.sqrt(Double.valueOf(sum) / list.size());
	}

}
