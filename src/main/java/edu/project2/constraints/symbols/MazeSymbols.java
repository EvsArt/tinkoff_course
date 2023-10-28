package edu.project2.constraints.symbols;

import edu.project2.model.Cell;
import edu.project2.model.DefaultCell;

public enum MazeSymbols implements Symbols {

    CELLWITHONLYLEFTWAY(DefaultCell.getCell(false, true, true, true), "⣉⣹"),
    CELLWITHONLYTOPWAY(DefaultCell.getCell(true, false, true, true), "⣇⣸"),
    CELLWITHONLYRIGHTWAY(DefaultCell.getCell(true, true, false, true), "⣏⣉"),
    CELLWITHONLYBOTTOMWAY(DefaultCell.getCell(true, true, true, false), "⡏⢹"),
    CELLWITHONLYLEFTWALL(DefaultCell.getCell(true, false, false, false), "⡇⠀"),
    CELLWITHONLYTOPWALL(DefaultCell.getCell(false, true, false, false), "⠉⠉"),
    CELLWITHONLYRIGHTWALL(DefaultCell.getCell(false, false, true, false), "⠀⢸"),
    CELLWITHONLYBOTTOMWALL(DefaultCell.getCell(false, false, false, true), "⣀⣀"),
    CELLLIKELEFTTOPCORNER(DefaultCell.getCell(true, true, false, false), "⡏⠉"),
    CELLLIKETOPRIGHTCORNER(DefaultCell.getCell(false, true, true, false), "⠉⢹"),
    CELLLIKERIGHTBOTTOMCORNER(DefaultCell.getCell(false, false, true, true), "⣀⣸"),
    CELLLIKEBOTTOMLEFTCORNER(DefaultCell.getCell(true, false, false, true), "⣇⣀"),
    CELLWITHTOPBOTTOMWALLS(DefaultCell.getCell(false, true, false, true), "⣉⣉"),
    CELLWITHLEFTRIGHTWALLS(DefaultCell.getCell(true, false, true, false), "⡇⢸"),
    CELLCLOSED(DefaultCell.getCell(true, true, true, true), "⣏⣹"),
    CELLOPENED(DefaultCell.getCell(false, false, false, false), "⠀⠀");

    private final Cell cell;
    private final String value;

    MazeSymbols(Cell cell, String value) {
        this.cell = cell;
        this.value = value;
    }

    public Cell getCell() {
        return cell;
    }

    public String getValue() {
        return value;
    }

}
