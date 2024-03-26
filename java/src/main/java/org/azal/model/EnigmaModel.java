package org.azal.model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * The EnigmaModel class represents a model for managing enigma-related data and logic in conjunction
 * with the Model-View-Controller (MVC) pattern. It provides functionalities for reading enigmas from
 * a file and selecting a random enigma.
 */
public class EnigmaModel {
    /** The list of enigmas read from the enigmas.txt file. */
    private List<String> enigmas;
    /**
     * Constructs a new EnigmaModel instance for reading enigmas from the enigmas.txt file.
     */
    public EnigmaModel() {
        try {
            enigmas = Files.readAllLines(Paths.get("src/data/enigmas.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a random enigma from the list of enigmas.
     *
     * @return A random enigma from the list of enigmas.
     */
    public String getRandomEnigma() {
        Random random = new Random();
        int randomIndex = random.nextInt(enigmas.size());
        return enigmas.get(randomIndex);
    }
}
