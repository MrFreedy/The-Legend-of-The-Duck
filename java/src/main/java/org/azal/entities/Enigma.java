package org.azal.entities;

public class Enigma {
    private String statement;
    private String answer;

    public Enigma(String statement, String answer) {
        this.statement = statement;
        this.answer = answer;
    }

    // getters and setters
    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
