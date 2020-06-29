package model.units;

import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class GasControlUnit extends FireUnit{
	public GasControlUnit(String id, Address location,int stepsPerCycle,WorldListener worldlistener) {
		super(id,location,stepsPerCycle,worldlistener);
	}

//	public GasControlUnit(String unitID, Address location, int stepsPerCycle) {
//
//		super(unitID, location, stepsPerCycle);
//
//	}
public void cycleStep() {
	super.cycleStep();
}
	public void treat() {
		this.getTarget().getDisaster().setActive(false);
		if(this.getTarget() instanceof ResidentialBuilding) {
			((ResidentialBuilding) getTarget()).setGasLevel(((ResidentialBuilding) getTarget()).getGasLevel()-10);
		}
		this.setState(UnitState.IDLE);
	}
}
