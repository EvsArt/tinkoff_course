package edu.project2.service.generator;

import edu.project2.exceptions.CellDoesNotHaveMarkedNeighborsException;
import edu.project2.model.Cell;
import edu.project2.model.CellWithPosition;
import edu.project2.model.Field;
import edu.project2.model.Neighbor;
import edu.project2.model.Position;
import edu.project2.model.SideEnum;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrimsGenerator implements Generator {

    Logger log = LogManager.getLogger();
    Random random = new Random();
    HashSet<Neighbor> potentialNexts = new HashSet<>();

    @Override
    public Field generate(Field field) {

        field.closeAllCells();
        field.unmarkAllCells();

        Position randomPos = new Position(random.nextInt(field.getWidth()), random.nextInt(field.getHeight()));
        Cell randomCell = field.getCell(randomPos);
        CellWithPosition randomCellPos = new CellWithPosition(randomCell, randomPos);

        putNeighborsToPotentialNextsSet(field, randomCellPos);
        randomCellPos.cell().mark(true);

        while (!potentialNexts.isEmpty()) {
            Neighbor nextCell = potentialNexts.stream().findFirst().get();  // getting random cell from set
            Neighbor markedNeighbor =
                getRandomMarkedNeighbor(field, new CellWithPosition(nextCell.cell(), nextCell.position()));
            field.changeWay(nextCell.position(), markedNeighbor.side(), false);
            nextCell.cell().mark(true);
            potentialNexts.remove(nextCell);
            putNeighborsToPotentialNextsSet(field, new CellWithPosition(nextCell.cell(), nextCell.position()));

        }

        field.unmarkAllCells();

        return field;

    }

    protected Neighbor getRandomMarkedNeighbor(Field field, CellWithPosition cellPos) {

        ArrayList<Neighbor> res = new ArrayList<>(SideEnum.values().length);

        Position pos = cellPos.position();
        int posX = cellPos.position().x();
        int posY = cellPos.position().y();
        Cell neighbor;

        if (posX > 0) {
            Position newPos = pos.getNewPosAfterMoving(SideEnum.LEFT);
            neighbor = field.getCell(newPos);
            if (neighbor.isMarked()) {
                res.add(new Neighbor(SideEnum.LEFT, neighbor, newPos));
            }
        }
        if (posX < field.getWidth() - 1) {
            Position newPos = pos.getNewPosAfterMoving(SideEnum.RIGHT);
            neighbor = field.getCell(newPos);
            if (neighbor.isMarked()) {
                res.add(new Neighbor(SideEnum.RIGHT, neighbor, newPos));
            }
        }
        if (posY > 0) {
            Position newPos = pos.getNewPosAfterMoving(SideEnum.TOP);
            neighbor = field.getCell(newPos);
            if (neighbor.isMarked()) {
                res.add(new Neighbor(SideEnum.TOP, neighbor, newPos));
            }
        }
        if (posY < field.getHeight() - 1) {
            Position newPos = pos.getNewPosAfterMoving(SideEnum.BOTTOM);
            neighbor = field.getCell(newPos);
            if (neighbor.isMarked()) {
                res.add(new Neighbor(SideEnum.BOTTOM, neighbor, newPos));
            }
        }

        if (res.isEmpty()) {
            log.error("Error in getRandomMarkedNeighbor() in PrimsGenerator: Cell doesn't have any marked cells");
            throw new CellDoesNotHaveMarkedNeighborsException("This Cell doesn't have any marked cells!");
        }
        return res.get(random.nextInt(res.size()));

    }

    private void putNeighborsToPotentialNextsSet(Field field, CellWithPosition cellPos) {

        Position curPosition = cellPos.position();
        int posX = curPosition.x();
        int posY = curPosition.y();
        Cell neighbor;

        if (posX > 0) {
            Position newPos = curPosition.getNewPosAfterMoving(SideEnum.LEFT);
            neighbor = field.getCell(newPos);
            if (!neighbor.isMarked()) {
                potentialNexts.add(new Neighbor(SideEnum.LEFT, neighbor, newPos));
            }

        }
        if (posX < field.getWidth() - 1) {
            Position newPos = curPosition.getNewPosAfterMoving(SideEnum.RIGHT);
            neighbor = field.getCell(newPos);
            if (!neighbor.isMarked()) {
                potentialNexts.add(new Neighbor(SideEnum.RIGHT, neighbor, newPos));
            }
        }
        if (posY > 0) {
            Position newPos = curPosition.getNewPosAfterMoving(SideEnum.TOP);
            neighbor = field.getCell(newPos);
            if (!neighbor.isMarked()) {
                potentialNexts.add(new Neighbor(SideEnum.TOP, neighbor, newPos));
            }
        }
        if (posY < field.getHeight() - 1) {
            Position newPos = curPosition.getNewPosAfterMoving(SideEnum.BOTTOM);
            neighbor = field.getCell(newPos);
            if (!neighbor.isMarked()) {
                potentialNexts.add(new Neighbor(SideEnum.BOTTOM, neighbor, newPos));
            }
        }

    }

}
