package org.azal.model;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class EnigmaModel {
    private List<String> enigmas;

    public EnigmaModel() {
        try {
            enigmas = Files.readAllLines(Paths.get("src/data/enigmas.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRandomEnigma() {
        Random random = new Random();
        int randomIndex = random.nextInt(enigmas.size());
        return enigmas.get(randomIndex);
    }
}