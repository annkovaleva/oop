package tanks;

import java.util.ArrayList;
import java.util.HashMap;

// Волновой навигатор
public class WaveNavigator{

    Field _field;

    public WaveNavigator(Field field) {
        this._field = field;
    };

    public ArrayList<Cell> findPath(Cell from, Cell to) {
        HashMap<Cell, Integer> cellMarks = waveProrogation(from, to);

        ArrayList<Cell> reversPath = pathRecovery(from, to, cellMarks);
        ArrayList<Cell> path = new ArrayList<>();
        for(int i = reversPath.size()-1; i >= 0; i--)
            path.add(reversPath.get(i));
        return path;
    }

    // Этап распространения волны по полю. Расставляются метки.
    private HashMap<Cell, Integer> waveProrogation(Cell from, Cell to) {
        HashMap<Cell, Integer> cellMarks = new HashMap<>();

        int mark = 1;
        for (Cell cell : _field.cells_) {
            cellMarks.put(cell, 0);
        }
        cellMarks.replace(from, mark);

        while(cellMarks.get(to) == 0 && mark <= cellMarks.size()) {
            for (Cell cell : _field.cells_) {
                if (cellMarks.get(cell) == mark) {
                    ArrayList<Direction> neighborsDir = cell.neighbors();
                    for (Direction obj : neighborsDir) {
                        if (cellMarks.get(cell.neighborCell(obj)) == 0)
                            cellMarks.replace(cell.neighborCell(obj), mark + 1);
                    }
                }
            }
            mark++;
        }
        return cellMarks;
    }

    // Восстановление пути
    private ArrayList<Cell> pathRecovery(Cell from, Cell to, HashMap<Cell, Integer> cellMarks) {
        ArrayList<Cell> path = new ArrayList<>();
        if(cellMarks.get(to) != 0) {
            path.add(to);
            Cell curCell = to;
            while (curCell != from) {
                ArrayList<Direction> neighborsDir = curCell.neighbors();
                for (int i = 0; i < neighborsDir.size(); i++) {
                    Direction dir = neighborsDir.get(i);
                    if (cellMarks.get(curCell.neighborCell(dir)) == cellMarks.get(curCell) - 1) {
                        curCell = curCell.neighborCell(dir);
                        path.add(curCell);
                        i = neighborsDir.size();
                    }
                }
            }
        }
        return path;
    }
}
