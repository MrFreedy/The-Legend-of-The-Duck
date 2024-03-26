package org.azal.entities;

/**
 * Represents an enigma.
 */
public class Enigma {
    /** The statement of the enigma. */
    private String statement;
    /** The answer of the enigma. */
    private String answer;

    /**
     * Constructs a new Enigma instance with the specified statement and answer.
     *
     * @param statement The statement of the enigma.
     * @param answer The answer of the enigma.
     */
    public Enigma(final String statement, final String answer) {
        this.statement = statement;
        this.answer = answer;
    }

    /**
     * Retrieves the statement of the enigma.
     *
     * @return The statement of the enigma.
     */
    public String getStatement() {
        return statement;
    }

    /**
     * Sets the statement of the enigma.
     *
     * @param statement The statement of the enigma.
     */
    public void setStatement(final String statement) {
        this.statement = statement;
    }

    /**
     * Retrieves the answer of the enigma.
     *
     * @return The answer of the enigma.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the answer of the enigma.
     *
     * @param answer The answer of the enigma.
     */
    public void setAnswer(final String answer) {
        this.answer = answer;
    }
}
