package edu.project1.application;

public final class HangmanMain {

    private HangmanMain() {
    }

    public static void main(String[] args) {
        Application app = new ConsoleHangman();
        app.run();

    }

}
