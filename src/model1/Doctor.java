package model1;

import java.util.ArrayList;
import java.util.Collections;

import model1.Room;

public class Doctor {
	//Categories - MA1, NP1, MA2, NP2, MA3, NP3, MA4, NP4, AMA, A, NMA, N, PMA, P 
	public static final boolean[] MA = {true,false,true,false,true,false,true,false,true,false,false,false,true,false}; //Medical Assistant
	public static final boolean[] NP = {false,true,false,true,false,true,false,true,false,false,false,false,false,false}; //Nurse Practitioner
	public static final boolean[] A =  {false,false,false,false,false,false,false,false,false,true,false,false,false,false}; //Audio
	public static final boolean[] N =  {false,false,false,false,false,false,false,false,false,false,false,true,false,false}; //Neurologist
	public static final boolean[] P =  {false,false,false,false,false,false,false,false,false,false,false,false,false,true}; //Psychiatrist



	private int[] occupied;
	private double cost;
	private ArrayList<Integer> startTimes;
	private ArrayList<Integer> endTimes;
	private ArrayList<Room> rooms;
	private int[] lengths;
	
	private int dayLength;
	private int startTime;
	private int endTime;

	//1 = MA, 2 = NP, 3 = Audio, 4 = Neurologist, 5 = Psychiatrist
	

	
	
	public enum DocType {
		MA, NP, A, N, P;
	}
	
	private DocType type;
	private boolean[] types;
	
	public Doctor(int cost, int dayLength, DocType type, int[] lengths){
		this.dayLength = dayLength;
		startTime = 0;
		startTimes = new ArrayList<Integer>();
		endTimes = new ArrayList<Integer>();
		rooms = new ArrayList<Room>();
		occupied = new int[dayLength];
		this.lengths = lengths;
		this.cost = cost;
		
		this.type = type;
		
		switch(type){
			case MA:
				types = MA;
				break;
			case NP:
				types = NP;
				break;
			case A:
				types = A;
				break;
			case N:
				types = N;
				break;
			case P:
				types = P;
				break;
		}
		
	}
	
	public static int totalWastedTime(Doctor[] doctors){
		int time = 0;
		for(Doctor d:doctors){
			time+=d.wastedTime();
		}
		return time;
	}
	
	public static double totalCost(Doctor[] doctors){
		double totCost = 0;
		for(Doctor d:doctors){
			totCost+=d.cost();
		}
		return totCost;
	}
	
	public double cost(){
		return (endTime-startTime)/60.0*cost;
	}
	
	//put the doc to use
	public void addTime(int time, int category, Room room){
		startTimes.add(time);
		endTimes.add(time+lengths[category]);
		rooms.add(room);
		for(int i = time; i < time+lengths[category]; i++){
			if(i >= dayLength) break;
			occupied[i] = 1;
		}
	}
	
	public int wastedTime(){
		if(startTimes.size()>0){
			int time = startTimes.get(0)-startTime;
			for(int i = 1; i < startTimes.size(); i++){
				time+=startTimes.get(i)-endTimes.get(i-1);
			}
			time += endTime-endTimes.get(endTimes.size()-1);
			return time;
		}
		return 0;
	}
	
	private void optimalStartEnd(){
		
		if(startTimes.size()>0){
			//startTime = ((startTimes.get(0))/60)*60;
			//endTime = (endTimes.get(endTimes.size()-1)/60+1)*60;
			startTime = startTimes.get(0);
			endTime = endTimes.get(endTimes.size()-1);
		}

	}
	
	public void finish(){
		Collections.sort(startTimes);
		Collections.sort(endTimes);
		optimalStartEnd();
	}
	
	
	public void update(){}
	
	
	
	public String toString(){
		return type.toString();
	}
	
	
	public boolean isBusy(int time) {
		return occupied[time] == 1;
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

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}

	public DocType getType() {
		return type;
	}

	public void setType(DocType type) {
		this.type = type;
	}

	public boolean[] getTypes() {
		return types;
	}

	public void setTypes(boolean[] types) {
		this.types = types;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	public int[] getOccupied() {
		return occupied;
	}

	public void setOccupied(int[] occupied) {
		this.occupied = occupied;
	}

}
