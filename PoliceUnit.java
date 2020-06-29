package model.units;

import java.util.ArrayList;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

abstract public class PoliceUnit extends Unit{
	private ArrayList<Citizen> passengers=new ArrayList<Citizen>()	 ;
	private int maxCapacity;
	private int distanceToBase;
	
	public ArrayList<Citizen> getPassengers() {
		return passengers;
	}
	
	public int getDistanceToBase() {
		return distanceToBase;
	}


	public void setDistanceToBase(int distanceToBase) {
		this.distanceToBase = distanceToBase;
	}


	public int getMaxCapacity() {
		return maxCapacity;
	}
	
	
//	public PoliceUnit(String unitID, Address location, int stepsPerCycle,int maxCapacity) {
//
//		super(unitID, location, stepsPerCycle);
//		this.maxCapacity=maxCapacity;
//
//	}
	public PoliceUnit(String id,Address location,int stepsPerCycle,WorldListener worldlistener ,int maxCapacity) {
		super(id,location,stepsPerCycle,worldlistener);
		this.maxCapacity=maxCapacity;	
	}
	
	public void cycleStep() {
		if(getDistanceToBase()>0) {
			this.setDistanceToBase(getDistanceToBase()-getStepsPerCycle());
		}else {
			if(getPassengers().size()!=0) {
				getWorldListener().assignAddress(this, 0, 0);
				while(!getPassengers().isEmpty()) {
					Citizen c = getPassengers().remove(0);
					if(c.getState()==CitizenState.DECEASED) {
						c.setState(CitizenState.DECEASED);
					}
					c.getWorldListener().assignAddress(this, 0, 0);
						c.setState(CitizenState.RESCUED);
					
				}
				setDistanceToTarget(getTarget().getLocation().getX()+getTarget().getLocation().getY());
				if(this.getDistanceToBase()<=0) {
					this.setDistanceToBase(0);
				}
			}else {
				super.cycleStep();
			}
		}
	}
	
	

}
