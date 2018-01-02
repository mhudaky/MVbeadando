package hu.elte.progtech.calculator.model;

public class CalculatorEngine {

    private static final int FIELDSIZE = 9;

    private Integer blueShips = FIELDSIZE - 1;
    private Integer redShips = FIELDSIZE - 1;
    private boolean shipIsAssigned = false;
    private boolean redIsOnTurn = true;

    public int getFieldSize() {
        return FIELDSIZE;
    }
    
    public boolean getshipIsAssigned() {
        return shipIsAssigned;
    }
    
    public boolean getRedIsOnTurn() {
        return redIsOnTurn;
    }

    public void pushShip() {
    	shipIsAssigned = true;
    }

    public void pushFieldToMove() {
    	shipIsAssigned = false;
    	redIsOnTurn = !redIsOnTurn;
    }

    public void shipGetsInto() {
        if (redIsOnTurn) {
        	redShips--;
        	if (isCompleted(redShips))
        		System.out.println("Red won!");
        }
        else {
        	blueShips--;
        	if (isCompleted(blueShips))
        		System.out.println("Blue won!");	
        }
    }

    public boolean isCompleted(int ships) {
        return ships == (FIELDSIZE / 2);
    }

}
