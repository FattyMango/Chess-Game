package engine;

import board.Board;
import board.ChessBoard;
import engine.Engine;
import pieces.King;
import pieces.Knight;
import pieces.Piece;
import position.*;
import rule.EnPassant;
import rule.Rule;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class ChessEngine implements Engine {
    private Board board;
    private boolean turn;
    private String winner;
    private boolean isWhiteKingChecked;
    private boolean isBlackKingChecked;
    private int moves;
    private int movesLimit;

    private ArrayList<Rule> rules;

    public ChessEngine( Board chessBoard){
        setBoard(Objects.requireNonNullElseGet(chessBoard, () -> new ChessBoard()));

        isWhiteKingChecked = false;
        isBlackKingChecked = false;
        turn = true;
        winner = "Draw";
       rules = new ArrayList<Rule>();
       setMovesLimit(20);
        addRule(new EnPassant());

    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public Piece getPiece(Position position) {
        return board.getPiece(position);
    }

    @Override
    public boolean isGameOver() {
        boolean isCheck = isCheckMate();
        if(isCheck)
            winner = turn? "Black" : "White";

        return  isCheck || moves >= movesLimit;
    }
    @Override
    public boolean getTurn() {return turn;}
    @Override
    public String getWinner() {return winner;}
    @Override
    public boolean makeMove(Position start, Position end){

        if(isValidMove(start, end)){

            Piece piece =board.getPiece(start) ;

            doMove(start,end,piece);

            piece.setFirstMove(false);
            if(turn)
                isBlackKingChecked = canAttackKing(board.getWhitePieces(),board.getBlackKingPosition());
            else
                isWhiteKingChecked = canAttackKing(board.getBlackPieces(),board.getWhiteKingPosition());
            moves++;
            turn = !turn;
            return true;
        }
        else {
            for(Rule rule : rules)
                if(rule.applyRule(start,end,board))
                    return true;
        }

        return false;

    }

    public void setMovesLimit(int movesLimit) {
        this.movesLimit = movesLimit;
    }

    public void addRule(Rule rule){
        rules.add(rule);
    }

    public boolean isValidMove(Position start, Position end){

        if(board.isPositionOccupied(start)) {
            Piece piece = board.getPiece(start);

            if(start.equals(end) || piece.getColor() != turn || exposesKing(start,end))
                return false;

            if (board.isPositionOccupied(end) && !(board.getPiece(end) instanceof King))
                return canAttackPosition(start, end) && isWayClear(start, end);

            else
                return piece.isLegalMove(start,end)&& isWayClear(start, end);

        }
        return false;
    }

    public boolean isWayClear(Position start, Position end){

        if(board.getPiece(start) instanceof Knight)
            return true;

        int x = start.getRow() - end.getRow();
        int y = start.getColumn() - end.getColumn();
        int xDirection = Integer.signum(x) *-1;
        int yDirection = Integer.signum(y) *-1;
        int count =1;
        while (count < Math.max(Math.abs(x),Math.abs(y))){
            try {
                Position p = new ChessPosition(((start.getRow() + (xDirection * count)) + 1), ((start.getColumn() + (yDirection * count)) + 1));
                if (board.isPositionOccupied(p))
                    return false;
            }
            catch (Exception e ){break;}
            count++;
        }
        return true;
    }


    public boolean canAttackKing(Stack<Position> opponentPieces, Position kingPosition) {

        for (Position position : opponentPieces)
            if (canAttackPosition(position, kingPosition) && isWayClear(position, kingPosition))
                return true;

        return false;
    }

    public boolean canAttackPosition(Position start, Position end){
        Piece attacker = board.getPiece(start);
        Piece target = board.getPiece(end);

        return (attacker.getColor() != target.getColor() && attacker.canAttackPiece(start, end));
    }



    public void doMove(Position start, Position end, Piece startPiece){
        board.removePiece(end);
        board.removePiece(start);
        board.setPiece(end, startPiece);
    }
    public void undoMove(Position start, Position end, Piece startPiece, Piece endPiece){
        board.removePiece(end);
        board.removePiece(start);
        board.setPiece(start,startPiece);
        board.setPiece(end,endPiece);
    }
    public boolean exposesKing(Position start , Position end) {
        Piece piece,target;
        Stack<Position> opponentPieces;
        Position kingPosition;
        boolean isKingAttacked;

        piece = board.getPiece(start);
        target= board.getPiece(end);

        doMove(start,end,piece);
        kingPosition    = turn? board.getWhiteKingPosition()   :board.getBlackKingPosition() ;
        opponentPieces  = turn? board.getBlackPieces()       : board.getWhitePieces();

        isKingAttacked = canAttackKing(opponentPieces, kingPosition);
        undoMove(start,end,piece,target);


        return isKingAttacked;
    }
    public boolean kingCanMove(){

        Position kingPosition;
        Stack<Position> opponentPieces;
        Piece king,target;
        boolean isKingAttacked;

        kingPosition    = turn? board.getWhiteKingPosition()   : board.getBlackKingPosition();
        opponentPieces  = turn?  board.getBlackPieces()      : board.getWhitePieces();

        king = board.getPiece(kingPosition);
        Stack<Position> kingMoves = ((King) king).possibleMoves(kingPosition);

        if(kingMoves.empty())
            return false;

        for(Position kingMove : kingMoves){
            target = board.getPiece(kingMove);
            if (!board.isPositionOccupied(kingMove) || target.getColor()!= king.getColor())
                for(Position piece : opponentPieces)
                    if(isWayClear(piece, kingMove)){

                    doMove(kingPosition,kingMove,king);
                    isKingAttacked = canAttackKing(opponentPieces,kingMove) ;
                    undoMove(kingPosition,kingMove,king,target);

                    if (!isKingAttacked)
                        return true;
                    }
        }

        return false;
    }
    public boolean pieceCanBlock(){
        Position kingPosition;
        Piece currentPiece , target;
        Stack<Position> pieces , opponentPieces , availablePositions;
        boolean isKingAttacked , pieceCanAttack , pieceCanMove;

        availablePositions  = board.availablePositions();
        kingPosition        = turn? board.getWhiteKingPosition() : board.getBlackKingPosition();
        pieces              = turn? board.getWhitePieces()       : board.getBlackPieces();
        opponentPieces      = turn? board.getBlackPieces()       : board.getWhitePieces();


        for (Position piecePosition : pieces){
            currentPiece = board.getPiece(piecePosition);
            if(!(currentPiece instanceof King)){
                for(Position position : availablePositions) {
                    target = board.getPiece(position);

                    if (!piecePosition.equals(position) && isWayClear(piecePosition, position)) {
                        pieceCanMove = (!board.isPositionOccupied(position) && (currentPiece.isLegalMove(piecePosition, position)));
                        pieceCanAttack = (board.isPositionOccupied(position) && currentPiece.canAttackPiece(piecePosition, position));
                        if ( pieceCanMove || pieceCanAttack ) {
                            doMove(piecePosition, position, currentPiece);
                            isKingAttacked = canAttackKing(opponentPieces, kingPosition);
                            undoMove(piecePosition, position, currentPiece, target);
                            if (!isKingAttacked)
                                return true;
                        }
                    }
                }
            }
        }

        return false;
    }
    public boolean isCheckMate() {

        if (turn)
            return (isWhiteKingChecked && !kingCanMove() && !pieceCanBlock());
        else
            return (isBlackKingChecked && !kingCanMove() && !pieceCanBlock());

    }

}
