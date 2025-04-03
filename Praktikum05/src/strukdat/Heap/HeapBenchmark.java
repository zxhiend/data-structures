package strukdat.Heap;

import java.util.Random;
import com.datastruct.Heap;

public class HeapBenchmark {
    public static void main(String[] args) {
        int[] sizes = { 10000, 20000, 40000, 80000 };
        Random random = new Random();

        for (int size : sizes) {
            Heap<Integer, Integer> heap = new Heap<>(size, false);
            for (int i = 0; i < size; i++) {
                int num = random.nextInt();
                heap.insert(num, num);
            }

            long startTime = System.currentTimeMillis();
            heap.sort();
            long endTime = System.currentTimeMillis();

            System.out.println("Heap Sort Time for " + size + " elements: " + (endTime - startTime) + " ms");
        }
    }
}
