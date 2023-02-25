package board;

import position.*;
import pieces.*;

import java.util.LinkedList;
import java.util.Stack;

public class ChessBoard implements Board{
    private Piece [][] board;

    private LinkedList<Piece> capturedPieces;
    public ChessBoard(){
        this.board = new Piece[8][8];
        this.capturedPieces = new LinkedList<Piece>();
        initBoard();
    }

    public void initBoard(){

        for (int i =1; i<=8;i++){
            setPiece(new ChessPosition(2,i),new Pawn("P",true));
            setPiece(new ChessPosition(7,i),new Pawn("P",false));
        }
        setPiece(new ChessPosition(1,1),new Rook("R",true));
        setPiece(new ChessPosition(1,8),new Rook("R",true));
        setPiece(new ChessPosition(8,1),new Rook("R",false));
        setPiece(new ChessPosition(8,8),new Rook("R",false));

        setPiece(new ChessPosition(1,2),new Knight("N",true));
        setPiece(new ChessPosition(1,7),new Knight("N",true));
        setPiece(new ChessPosition(8,2),new Knight("N",false));
        setPiece(new ChessPosition(8,7),new Knight("N",false));

        setPiece(new ChessPosition(1,3),new Bishop("B",true));
        setPiece(new ChessPosition(1,6),new Bishop("B",true));
        setPiece(new ChessPosition(8,3),new Bishop("B",false));
        setPiece(new ChessPosition(8,6),new Bishop("B",false));

        setPiece(new ChessPosition(1,4),new Queen("Q",true));
        setPiece(new ChessPosition(1,5),new King("K",true));
        setPiece(new ChessPosition(8,4),new Queen("Q",false));
        setPiece(new ChessPosition(8,5),new King("K",false));

    }
    public Piece getPiece(Position position){

        return board[position.getRow()][position.getColumn()];
    }
    public boolean isPositionOccupied(Position position){
        return getPiece(position) != null;
    }
    public boolean setPiece(Position position, Piece piece){

        board[position.getRow()][position.getColumn()] = piece;
        capturedPieces.remove(piece);

        return true;
    }
    public boolean removePiece(Position position){
        if(isPositionOccupied(position)){

            Piece piece = getPiece(position);
            capturedPieces.add(piece);
            board[position.getRow()][position.getColumn()] = null;

            return true;
        }
        return false;

    }
    public Stack<Position>  availablePositions(){
        Stack<Position> positions = new Stack<Position>();

        for (int i = 1; i<=8; i++)
            for (int j = 1; j<=8; j++)
                positions.add(new ChessPosition(i,j));

        return positions;
    }
    public Stack<Position> getWhitePieces() {
        Stack<Position> whitePieces, availablePositions;
        whitePieces = new Stack<Position>();
        availablePositions = availablePositions();

        for (Position position : availablePositions)
                if(isPositionOccupied(position) && getPiece(position).getColor())
                    whitePieces.push(position);
        return whitePieces;
    }

    public Stack<Position> getBlackPieces() {
        Stack<Position> blackPieces , availablePositions;
        blackPieces = new Stack<Position>();
        availablePositions = availablePositions();

        for (Position position : availablePositions)
            if(isPositionOccupied(position) && !getPiece(position).getColor())
                blackPieces.push(position);
        return blackPieces;
    }

    public LinkedList<Piece> getCapturedPieces() {
        return capturedPieces;
    }



    public Position getBlackKingPosition(){
        Stack<Position> availablePositions = availablePositions();

        for (Position position : availablePositions) {
                Piece piece = getPiece(position);
                if((piece instanceof King) && !piece.getColor())
                    return position;
            }
        return new ChessPosition(1,1);
    }
    public Position getWhiteKingPosition(){
        Stack<Position> availablePositions = availablePositions();

        for (Position position : availablePositions) {
            Piece piece = getPiece(position);
            if((piece instanceof King) && piece.getColor())
                return position;
        }

        return new ChessPosition(1,1);
    }

}
