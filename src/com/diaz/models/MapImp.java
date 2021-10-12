package com.diaz.models;

import com.diaz.exceptions.MapException;
import com.diaz.exceptions.RobotException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapImp extends AbstractMap {

    public MapImp(int xMax, int yMax) throws MapException {
        this(0, 0, xMax, yMax);
    }

    public MapImp(int xMin, int yMin, int xMax, int yMax) throws MapException {
        super(xMin, yMin, xMax, yMax);
        if (xMin >= xMax)
            throw new MapException("xMin can not be greater or equal to xMax");
        if (yMin >= yMax)
            throw new MapException("yMin can not be greater or equal to yMax");
    }


}
