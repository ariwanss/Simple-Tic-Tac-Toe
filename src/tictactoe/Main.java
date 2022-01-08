package tictactoe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        List<List<String>> board = new ArrayList<>();
        board.add(Arrays.asList(" ", " ", " "));
        board.add(Arrays.asList(" ", " ", " "));
        board.add(Arrays.asList(" ", " ", " "));

        boolean game = true;
        String player = "X";

        while (game) {
            printBoard(board);
            System.out.print("Enter the coordinates: ");
            List<Integer> position;
            int row;
            int column;

            try {
                position = Stream.of(scanner.nextLine().split("\\s")).map(Integer::parseInt)
                        .collect(Collectors.toList());
                row = position.get(0);
                column = position.get(1);
            } catch (Exception E) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (!isInputWithinRange(row, column)) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (!isCellEmpty(board, row, column)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            updateBoard(board, row, column, player);
            printBoard(board);
            game = checkStatus(board);
            player = switchPlayer(player);
        }
    }

    private static boolean checkStatus(List<List<String>> board) {
        List<String> boardSequence = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            boardSequence.addAll(board.get(i));
        }

        if (hasWon(boardSequence, "O")) {
            System.out.println("O wins");
            return false;
        } else if (hasWon(boardSequence, "X")) {
            System.out.println("X wins");
            return false;
        } else if (isBoardFull(boardSequence)) {
            System.out.println("Draw");
            return false;
        } else {
            return true;
        }
    }

    private static String switchPlayer(String currentPlayer) {
        return Objects.equals(currentPlayer, "X") ? "O" : "X";
    }

    private static void printBoard(List<List<String>> board) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            board.get(i).forEach(x -> System.out.print(x + " "));
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }

    private static void updateBoard(List<List<String>> board, int row, int column, String player) {
        List<String> rowToUpdate = board.get(row - 1);
        rowToUpdate.set(column - 1, player);
        board.set(row - 1, rowToUpdate);
    }

    private static boolean isInputWithinRange(int row, int column) {
        return row < 4 && column < 4;
    }

    private static boolean isCellEmpty(List<List<String>> board, int row, int column) {
        return " ".equals(board.get(row - 1).get(column - 1));
    }

    private static boolean hasWon(List<String> input, String c) {
        boolean won = false;
        for (int i = 0; i < 9; i += 3) {
            won = won || input.subList(i, i + 3).stream().filter(c::equals).count() == 3;
        }
        for (int i = 0; i < 3; i += 1) {
            won = won || Stream.of(input.get(i), input.get(i + 3), input.get(i + 6)).filter(c::equals).count() == 3;
        }
        for (int i = 0; i < 4; i += 2) {
            won = won || Stream.of(
                    input.get(i),
                    input.get(i + (input.size() / (2 + i))),
                    input.get(i + 2 * (input.size() / (2 + i)))
            ).filter(c::equals).count() == 3;
        }
        return won;
    }

    private static boolean isBoardFull(List<String> board) {
        return !board.contains(" ");
    }
}


