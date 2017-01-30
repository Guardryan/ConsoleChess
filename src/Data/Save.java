package Data;

import Chess.ChessBoard;
import Chess.Tile;

import java.io.*;

/**
 * Created by Ryan on 1/29/2017.
 */

public class Save {

    public static void clearAutoSave(){
        BufferedWriter writer = null;

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



    public static void AutoSave(ChessBoard board) throws IOException {
        BufferedWriter autoSaveFile = new BufferedWriter(
                new FileWriter("C:\\Users\\Ryan\\Documents\\GitHub\\ConsoleChess\\src\\Data\\AutoSave.txt",
                        true));
        Tile[][] currentBoard = board.getBoardArray();
        for(int i =0; i < currentBoard.length; i++){
            for(int x = 0; x < currentBoard[i].length; x++) {
                autoSaveFile.append(currentBoard[i][x].toString());

                if(x==7){
                    autoSaveFile.newLine();
                }
                autoSaveFile.flush();
            }
        }
        autoSaveFile.newLine();
        autoSaveFile.flush();
        autoSaveFile.close();
    }

    //TODO GuardRyan: make save copy over autoSave contents
    public static void Save(String str) throws IOException {
        File autoSaveFile = new File("C:\\Users\\Ryan\\Documents\\GitHub\\ConsoleChess\\src\\Data\\AutoSave.txt");
        File saveFile = new File("C:\\Users\\Ryan\\Documents\\GitHub\\ConsoleChess\\src\\Data\\" + str + ".txt");
        if(!saveFile.exists()){
            saveFile.createNewFile();
        }
        InputStream input = null;
        OutputStream output = null;
        try {

			/* FileInputStream to read streams */
            input = new FileInputStream(autoSaveFile);

			/* FileOutputStream to write streams */
            output = new FileOutputStream(saveFile);

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            output.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (null != input) {
                input.close();
            }
            if (null != output) {
                output.close();
            }
        }
    }
}