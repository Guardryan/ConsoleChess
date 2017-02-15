package Chess.Pieces;

import Chess.Move;

/**
 * ChessPiece is an abstract class that all other pieces extend.  It defines the movement of the piece from the current
 * location.
 */

public abstract class ChessPiece {
    public PieceType type;
    public PieceColor color;
    public Move[] moves;
    public String name;
    public String shortName;
    public boolean repeatableMoves;

    /**
     * Creates an abstract chess piece object.
     *
     * @param type the kind of piece this is
     * @param color either white or black
     * @param repeatableMoves whether moves extend to the edge of the board
     */
    protected ChessPiece(PieceType type, PieceColor color, boolean repeatableMoves){
        this.type = type;
        this.color = color;
        this.moves = moves;
        this.repeatableMoves = repeatableMoves;
        name = type.name();
        shortName = type.name().trim().substring(0,2);
    }

    /**
     * Valid pieces in Chess
     */
    public enum PieceType {
        Pawn, Rook, Knight, Bishop, Queen, King
    }

    /**
     * Colors of pieces in Chess
     */
    public enum PieceColor {
        White, Black
    }

    /**
     * @return an array of Moves that are valid for the piece
     */
    abstract public Move[] moves();

    /**
     * @return the long name of the piece eg. "Pawn"
     */
    public String name(){ return name; }

    /**
     * @return color of the piece eg. White or Black
     */
    public PieceColor color(){ return color; }

    /**
     * @return the single letter character value of the piece eg. "P" for Pawn
     */
    public String shortName(){ return shortName; }

    /**
     * @return whether the piece can continue movement until it is either blocked by another piece, can capture,
     * or the side of the board
     */
    public boolean repeatableMoves(){ return repeatableMoves; }

    // TODO: 1/16/2017 opponent can be changed so that it tests 'this' against another piece to determine if they are opponents returning a boolean
    /**
     * gets the opposite color of the parameter's color
     * @param color the color of a piece
     * @return the opposite color
     */
    public static PieceColor opponent(PieceColor color) {
        return (color == PieceColor.Black) ? PieceColor.White : PieceColor.Black;
    }

}
