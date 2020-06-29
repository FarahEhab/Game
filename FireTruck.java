package model.units;

import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class FireTruck extends FireUnit{
	public FireTruck(String id, Address location,int stepsPerCycle,WorldListener worldlistener) {
		super(id,location,stepsPerCycle,worldlistener);
	}
//	public FireTruck(String unitID, Address location, int stepsPerCycle) {
//
//		super(unitID, location, stepsPerCycle);
//
//	}

	public void treat() {
		this.getTarget().getDisaster().setActive(false);
		if(this.getTarget() instanceof ResidentialBuilding) {
			((ResidentialBuilding) getTarget()).setFireDamage(((ResidentialBuilding) getTarget()).getFireDamage()-10);
		}
		this.setState(UnitState.IDLE);
	}
}