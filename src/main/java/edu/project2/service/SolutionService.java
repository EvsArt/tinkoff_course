package edu.project2.service;

import edu.project2.exceptions.WrongPositionsMapException;
import edu.project2.model.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SolutionService {

    private static final Logger LOGGER = LogManager.getLogger();

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
            if (newPosition == null) {
                String msg = "Positions map is wrong, so it's impossible to restore a solution!";
                LOGGER.error("Error in restoreSolution() in SolutionService: " + msg);
                throw new WrongPositionsMapException(msg);
            }
            prevPosition = newPosition;
            res.add(newPosition);

        }

        return res.reversed();

    }

}
