package model.units;

import model.events.WorldListener;
import simulation.Address;

abstract public class FireUnit extends Unit{
	
	
	public FireUnit(String id, Address location,int stepsPerCycle,WorldListener worldlistener){
		super(id,location,stepsPerCycle,worldlistener);
	}
	
//	public FireUnit(String unitID, Address location, int stepsPerCycle) {
//
//		super(unitID, location, stepsPerCycle);
//
//	}
}
