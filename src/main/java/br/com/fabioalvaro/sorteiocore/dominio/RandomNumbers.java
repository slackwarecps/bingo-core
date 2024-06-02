package br.com.fabioalvaro.sorteiocore.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class RandomNumbers {

    /**
     * Generates a list of unique random numbers within a specified range.
     *
     * @param count the number of random numbers to generate
     * @param min   the minimum value (inclusive) of the random numbers
     * @param max   the maximum value (inclusive) of the random numbers
     * @return a list of unique random numbers
     */
    public List<Integer> generateRandomNumbers(int count, int min, int max, List<Integer> excludedNumbersList) {
        Random random = new Random();
        Set<Integer> randomNumbers = new HashSet<>();
        Set<Integer> excludedNumbers = new HashSet<>(excludedNumbersList);
        // Adiciona os números excluídos ao conjunto

        while (randomNumbers.size() < count) {
            int randomNumber = random.nextInt((max - min) + 1) + min;
            if (!excludedNumbers.contains(randomNumber)) {
                randomNumbers.add(randomNumber);
            }
        }
        // Ordena do menor para o maior pra facilitar a leitura
        List<Integer> sortedRandomNumbers = new ArrayList<>(randomNumbers);
        Collections.sort(sortedRandomNumbers);

        return new ArrayList<>(sortedRandomNumbers);
    }

}
