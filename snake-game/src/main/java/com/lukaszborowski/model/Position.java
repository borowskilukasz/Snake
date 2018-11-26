package com.lukaszborowski.model;


public class Position {

	//----------------------------------------------------------------------------------------------
	//private fields
	//------------------------------------------------------------------------------------------------
	private long timeOfCreate;
    private int row, col;

	//----------------------------------------------------------------------------------------------
	//constructor
	//------------------------------------------------------------------------------------------------
	public Position(int col, int row) {
        this.col = col;
        this.row = row;
        this.setTimeOfCreate(System.currentTimeMillis());
    }
	

	//----------------------------------------------------------------------------------------------
	//methods
	//------------------------------------------------------------------------------------------------
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position pair = (Position) o;

        if (this.col != pair.col) return false;
        if (this.row != pair.row) return false;

        return true;
    }


	//----------------------------------------------------------------------------------------------
	//getters and setters
	//------------------------------------------------------------------------------------------------
    public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}


	public long getTimeOfCreate() {
		return timeOfCreate;
	}

	public void setTimeOfCreate(long timeOfCreate) {
		this.timeOfCreate = timeOfCreate;
	}
}
