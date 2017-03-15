package Chess;

import Chess.Pieces.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Chess Board is a class for the board that is able to set up the starting positions of the pieces for a game of chess
 * as well as return coordinates that correspond to the values of the chess pieces locations including a
 * specific method for the King's location.
 */

public class ChessBoard implements Cloneable {
    private ArrayList<ChessPiece> pieces;

    /**
     * Initializes the chess board of a 8x8 2d Tile array
     */
    public ChessBoard(){
        fillBoard();
    }

    public ChessBoard(ArrayList<ChessPiece> board) {
        pieces = (ArrayList<ChessPiece>) board.clone();
    }

    public ChessPiece getPieceAtLocation(Location location) {
        for (ChessPiece piece : pieces) {
            if (piece.getLocation().equals(location)) {
                return piece;
            }
        }
        return null;
    }

    public static boolean isInsideBoard(Location from, Location to) {
        return isInsideBoard(from) && isInsideBoard(to);
    }

    public static boolean isInsideBoard(Location location) {
        return location.x >= 0 && location.x <= 7 && location.y >= 0 && location.y <= 7;
    }

    public boolean removePiece(Location location) {
        return removePiece(getPieceAtLocation(location));
    }

    public boolean removePiece(ChessPiece pieceToRemove) {
        return pieces.remove(pieceToRemove);
    }

    public void move(Move move) {
        ChessPiece piece = move.getPiece();
        Location to = move.getTo();
        removePiece(piece);//remove current piece
        removePiece(to);//remove piece at to location
        piece.setLocation(to);
        pieces.add(piece);
    }

    public boolean isColorInCheck(ChessPiece.PieceColor color) {
        return getKingPiece(color).numPiecesThreateningThis(this) > 0;
    }

    public ArrayList<Move> getAllValidMoves(ChessPiece.PieceColor color) {
        ArrayList<Move> moves = new ArrayList<>();

        for (ChessPiece chessPiece : getAllPiecesLocationForColor(color)) {
            moves.addAll(chessPiece.validMoves(this));
        }

        return moves;
    }

    /**
     *
     * @return A deep copy of ChessBoard
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {

        ChessBoard clone = (ChessBoard)super.clone();
        ArrayList<ChessPiece> clonedPieces = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            clonedPieces.add((ChessPiece)piece.clone());
        }
        clone.pieces = clonedPieces;
        return clone;

    }

    /**
     * Returns the array of tiles that consists of the board.
     */
    public ArrayList<ChessPiece> getBoardArrayList(){
        return pieces;
    }

    public ChessPiece getKingPiece(ChessPiece.PieceColor color) {
        for (ChessPiece piece : pieces) {
            if(piece.color() == color && piece instanceof King) {
                return piece;
            }
        }
        return null;
    }

    /**
     * returns an array of coordinates containing the positions of the pieces of a color
     * @param color
     * @return coordinate array of pieces of that color
     */
    public ArrayList<ChessPiece> getAllPiecesLocationForColor(ChessPiece.PieceColor color){
        ArrayList<ChessPiece> piecesLocations = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            if (piece.color() == color) {
                piecesLocations.add(piece);
            }
        }
        return piecesLocations;
    }

    /**
     * Populates the board with the chess pieces of a typical starting slate of a board.
     */
    private void fillBoard(){
        pieces = new ArrayList<>();
        // Pawns
        for(int i = 0; i < 8; i++){
            pieces.add(new Pawn(ChessPiece.PieceColor.Black, new Location(i, 1)));
            pieces.add(new Pawn(ChessPiece.PieceColor.White, new Location(i, 6)));
        }

        // Rooks
        pieces.add(new Rook(ChessPiece.PieceColor.Black, new Location(0, 0)));
        pieces.add(new Rook(ChessPiece.PieceColor.Black, new Location(7, 0)));
        pieces.add(new Rook(ChessPiece.PieceColor.White, new Location(0, 7)));
        pieces.add(new Rook(ChessPiece.PieceColor.White, new Location(7, 7)));

        // Knight
        pieces.add(new Knight(ChessPiece.PieceColor.Black, new Location(1, 0)));
        pieces.add(new Knight(ChessPiece.PieceColor.Black, new Location(6, 0)));
        pieces.add(new Knight(ChessPiece.PieceColor.White, new Location(1, 7)));
        pieces.add(new Knight(ChessPiece.PieceColor.White, new Location(6, 7)));

        // Bishop
        pieces.add(new Bishop(ChessPiece.PieceColor.Black, new Location(2, 0)));
        pieces.add(new Bishop(ChessPiece.PieceColor.Black, new Location(5, 0)));
        pieces.add(new Bishop(ChessPiece.PieceColor.White, new Location(5, 7)));
        pieces.add(new Bishop(ChessPiece.PieceColor.White, new Location(2, 7)));

        // Queens
        pieces.add(new Queen(ChessPiece.PieceColor.Black, new Location(3, 0)));
        pieces.add(new Queen(ChessPiece.PieceColor.White, new Location(3, 7)));

        // Kings
        pieces.add(new King(ChessPiece.PieceColor.Black, new Location(4, 0)));
        pieces.add(new King(ChessPiece.PieceColor.White, new Location(4, 7)));
    }

    public ArrayList<Move> getPotentialMoves(ChessPiece.PieceColor color) {
        ArrayList<Move> potentialMoves = new ArrayList<>();
        for (ChessPiece piece : pieces) {
            if (piece.color().equals(color)) {
                potentialMoves.addAll(piece.potentialMoves(this));
            }
        }
        return potentialMoves;
    }

    // TODO: 3/14/2017 change to use ArrayList instead of the 2D Tile Array
    @Override
    public String toString() {
        Collections.sort(pieces);
        Iterator iter = pieces.iterator();
        ChessPiece piece = null;
        if (iter.hasNext()) {
            piece = (ChessPiece) iter.next();
        }
        String string = "";

        for(int y = 0; y < 8; y++) { //8 represents height of board
            for (int x = 0; x < 8; x++){ //8 represents width of board
                if (piece != null && piece.getLocation().equals(new Location(x, y))) {
                    String letter = piece.getLetter().toUpperCase();
                    if (piece.getColor().equals(ChessPiece.PieceColor.White)) {
                        letter = letter.toLowerCase();
                    }
                    string += "[" + letter + "]";
                    if (iter.hasNext()) {
                        piece = (ChessPiece) iter.next();
                    } else {
                        piece = null;
                    }
                } else {
                    string += ("[ ]");
                }
            }
            string += "\n";
        }
        return string;
    }
}
