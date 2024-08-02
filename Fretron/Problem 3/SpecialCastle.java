import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SpecialCastle {

    static class Position {
        int row, col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return row == position.row && col == position.col;
        }

        @Override
        public int hashCode() {
            return row * 31 + col;
        }

        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }
    }

    static class Path {
        List<Position> steps = new ArrayList<>();

        void addStep(Position pos) {
            steps.add(pos);
        }

        void printPath() {
            for (int i = 0; i < steps.size(); i++) {
                if (i == 0) {
                    System.out.print("Start " + steps.get(i) + "\n");
                } else if (i == steps.size() - 1) {
                    System.out.print("Arrive " + steps.get(i) + "\n");
                } else {
                    System.out.print("Kill " + steps.get(i) + ". Turn Left\n");
                }
            }
        }
    }

    static int boardSize = 8;
    static Set<Position> soldiers = new HashSet<>();
    static Position start;
    static List<Path> uniquePaths = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of soldiers: ");
        int soldierCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= soldierCount; i++) {
            System.out.print("Enter coordinates for soldier " + i + ": ");
            String[] parts = scanner.nextLine().split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            soldiers.add(new Position(row, col));
        }

        System.out.print("Enter coordinates for your “special” castle: ");
        String[] parts = scanner.nextLine().split(",");
        int startRow = Integer.parseInt(parts[0]);
        int startCol = Integer.parseInt(parts[1]);
        start = new Position(startRow, startCol);

        // Find all unique paths
        findPaths(new Path(), start, start);

        // Print all unique paths
        System.out.println("Thanks. There are " + uniquePaths.size() + " unique paths for your `special_castle` ");
        for (int i = 0; i < uniquePaths.size(); i++) {
            System.out.println("Path " + (i + 1) + ":");
            System.out.println("=======");
            uniquePaths.get(i).printPath();
            System.out.println();
        }

        scanner.close();
    }

    private static void findPaths(Path currentPath, Position current, Position origin) {
        if (current.equals(origin) && currentPath.steps.size() > 1) {
            uniquePaths.add(currentPath);
            return;
        }

        for (Position soldier : new HashSet<>(soldiers)) {
            if (soldier.row == current.row && soldier.col > current.col) {
                // Kill the soldier and move left
                soldiers.remove(soldier);
                Path newPath = new Path();
                newPath.steps.addAll(currentPath.steps);
                newPath.addStep(soldier);
                Position nextPos = new Position(current.row, soldier.col);
                findPaths(newPath, nextPos, origin);
                soldiers.add(soldier);
            }
        }
    }
}