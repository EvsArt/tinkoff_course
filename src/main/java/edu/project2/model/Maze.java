package edu.project2.model;

import edu.project2.service.Generator;

public class Maze {

    private final Field field;

    public Maze(int height, int width, Generator generator) {

        Field emptyField = new DefaultField(height, width);
        field = generator.generate(emptyField);

    }

    public Field getField() {
        return field;
    }

    @Override
    public String toString() {
        return field.toString();
    }

}
