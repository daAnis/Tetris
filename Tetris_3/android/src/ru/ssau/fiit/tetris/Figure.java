package ru.ssau.fiit.tetris;

import java.io.Serializable;

public class Figure implements Serializable {
    protected String figureId;

    protected int level;
    protected String structureString;

    public Figure() { }

    public Figure(String figureId, int level, String structureString) {
        this.figureId = figureId;
        this.level = level;
        this.structureString = structureString;
    }

    public String getFigureId() { return figureId; }
    public void setFigureId(String figureId) { this.figureId = figureId; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public String getStructureString() { return structureString; }
    public void setStructureString(String structureString) { this.structureString = structureString; }
}
