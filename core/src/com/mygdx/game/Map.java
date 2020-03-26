package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class Map {

    List<List<Cell>> map;

    public enum CellValue {
        BLOCKED,
        CLEAR
    }

    public class Cell {
        public Cell() {
            this.value = CellValue.CLEAR;
        }
        public CellValue value;
    }

    public Map() {

        for(int y = 0; y < Constants.NUM_CELLS; y++) {
            map.add(new ArrayList<Cell>(Constants.NUM_CELLS));
            for(int x = 0; x < Constants.NUM_CELLS; x++) {
                map.get(y).add(new Cell());
            }
        }
    }

    public Cell Get(int x, int y) {
        if(y >= Constants.NUM_CELLS) {
            throw new IllegalArgumentException("Invalid y");
        }

        if(x >= Constants.NUM_CELLS) {
            throw new IllegalArgumentException("Invalid x");
        }

        return map.get(y).get(x);
    }

}
