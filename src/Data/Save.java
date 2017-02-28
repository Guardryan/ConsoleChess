package Data;

import Chess.ChessBoard;
import Chess.Pieces.ChessPiece;
import Chess.Tile;

import java.io.*;

/**
 * Save system for replaying and retaining moves.
 */

public class Save {
    public enum Tags{
        BLACK("[B]"),
        WHITE("[W]"),
        BLANK("[ ]");
        private String value;
        Tags(String value){
                this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Clears out the current autosave to allow for a new game to write to the autosave file
     */
    public static void clearAutoSave(){
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new
                    FileWriter("C:\\Users\\Ryan\\Documents\\GitHub\\ConsoleChess\\src\\Data\\AutoSave.txt"));
            writer.append("");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Autosaves the current save to the board
     *
     * @param board
     * @throws IOException
     */
    public static void autoSave(ChessBoard board) throws IOException {
        BufferedWriter autoSaveFile = new BufferedWriter(
                new FileWriter("C:\\Users\\Ryan\\Documents\\GitHub\\ConsoleChess\\src\\Data\\AutoSave.txt",
                        true));
        Tile[][] currentBoard = board.getBoardArray();
        for(int i = 0; i < currentBoard.length; i++){
            for(int x = 0; x < currentBoard[i].length; x++) {
                if(currentBoard[x][i]!= null) {
                    autoSaveFile.append(currentBoard[x][i].toString());
                    if (currentBoard[x][i].getPiece().color() == ChessPiece.PieceColor.Black) {
                        autoSaveFile.append(Tags.BLACK.getValue());
                    } else if (currentBoard[x][i].getPiece().color() == ChessPiece.PieceColor.White) {
                        autoSaveFile.append(Tags.WHITE.getValue());
                    }
                } else {
                    autoSaveFile.append(Tags.BLANK.getValue());
                    autoSaveFile.append(Tags.BLANK.getValue());
                }
                if(x == 7){
                    autoSaveFile.newLine();
                }
                autoSaveFile.flush();
            }
        }
        autoSaveFile.flush();
        autoSaveFile.close();
    }

    /**
     *  Save takes the fromStr file and copies it into the toStr file
     *
     *  This is usually used for taking the auto save and writing it to a new file or taking a load file and loading
     *  it into the auto
     *
     * @param fromStr
     * @param toStr
     * @throws IOException
     */
    public static void save(String fromStr,String toStr) throws IOException {
        File autoSaveFile = new File("C:\\Users\\Ryan\\Documents\\GitHub\\ConsoleChess\\src\\Data\\" +
                fromStr + ".txt");
        File saveFile = new File("C:\\Users\\Ryan\\Documents\\GitHub\\ConsoleChess\\src\\Data\\" +
                toStr + ".txt");
        if(!saveFile.exists()){
            saveFile.createNewFile();
        }
        try {
			/* FileInputStream to read streams */
            InputStream input = new FileInputStream(autoSaveFile);
			/* FileOutputStream to write streams */
            OutputStream output = new FileOutputStream(saveFile);

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            output.flush();
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}