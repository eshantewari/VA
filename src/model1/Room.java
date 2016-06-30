package model1;

import java.util.ArrayList;
import java.util.Collections;

public class Room {
	
	//Categories - PMA1, MA1, NP1, PMA2, MA2, NP2, PMA3, MA3, NP3, PMA4, MA4, NP4, PAMA, AMA, A, PNMA, NMA, N, PPMA, PMA, P (Type of Exam)
	public static final boolean[] TRIAGE = {false,true,false,false,true,false,false,true,false,false,true,false,false,true,false,false,true,false,false,true,false}; //Triage
	public static final boolean[] GM =     {false,false,true,false,false,true,false,false,true,false,false,true,false,false,false,false,false,true,false,false,false}; //General Medicine Rooms
	public static final boolean[] A =      {false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false}; //Audio Room
	public static final boolean[] P =      {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true}; //Psych Room
	public static final boolean[] PLANNING = {true,false,false,true,false,false,true,false,false,true,false,false,true,false,false,true,false,false,true,false,false};

	public enum RoomType{
		TRIAGE, GM, A, P, PLANNING;
	}
	
	private String name;
	private int[] occupied;
	private ArrayList<Integer> startTimes;
	private ArrayList<Integer> endTimes;
	
	private RoomType type;
	private boolean[] types;
	private int[] lengths;
	private int dayLength;
	
	public Room(int dayLength, RoomType type, int[] lengths, int index){
		this.dayLength = dayLength;
		startTimes = new ArrayList<Integer>();
		endTimes = new ArrayList<Integer>();
		this.type = type;
		this.lengths = lengths;
		occupied = new int[dayLength];
		
		switch(type){
			case TRIAGE:
				types = TRIAGE;
				break;
			case GM:
				types = GM;
				break;
			case A:
				types = A;
				break;
			case P:
				types = P;
				break;
			case PLANNING:
				types = PLANNING;
				break;
		}
		
		name = type.toString()+" "+index;
	}
	
	//put the room to use
	public void addTime(int time, int category){
		startTimes.add(time);
		endTimes.add(time+lengths[category]);
		for(int i = time; i < time+lengths[category]; i++){
			if(i >= dayLength) break;
			occupied[i] = 1;
		}
	}
	

	public void finish(){
		Collections.sort(startTimes);
		Collections.sort(endTimes);
	}
	
	public void update(){}
	
	public String toString(){
		return name;
	}

	public boolean isBusy(int time) {
		if(this.type == RoomType.PLANNING) return false;
		return occupied[time] == 1;
	}

	public int[] getOccupied() {
		return occupied;
	}

	public void setOccupied(int[] occupied) {
		this.occupied = occupied;
	}

	public ArrayList<Integer> getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(ArrayList<Integer> startTimes) {
		this.startTimes = startTimes;
	}

	public ArrayList<Integer> getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(ArrayList<Integer> endTimes) {
		this.endTimes = endTimes;
	}

	public RoomType getType() {
		return type;
	}

	public void setType(RoomType type) {
		this.type = type;
	}

	public boolean[] getTypes() {
		return types;
	}

	public void setTypes(boolean[] types) {
		this.types = types;
	}
	
}
