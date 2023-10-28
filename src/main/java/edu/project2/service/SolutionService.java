package edu.project2.service;

import edu.project2.model.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SolutionService {

    private SolutionService() {
    }

    public static List<Position> restoreSolution(
        Map<Position, Position> posToPrevPosMap,
        Position startPos,
        Position endPos
    ) {

        ArrayList<Position> res = new ArrayList<>();
        Position prevPosition = endPos;
        res.add(prevPosition);
        Position newPosition = endPos;

        while (!newPosition.equals(startPos)) {

            newPosition = posToPrevPosMap.get(prevPosition);
            prevPosition = newPosition;
            res.add(newPosition);

        }

        return res.reversed();

    }

}
