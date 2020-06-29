package model.disasters;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;

public class GasLeak extends Disaster{

	public GasLeak(int cycle, ResidentialBuilding target) {
		super(cycle,target);

	}

	
	public void cycleStep() {
		ResidentialBuilding b= (ResidentialBuilding)getTarget();
		b.setGasLevel(b.getGasLevel()+15);
		
	}
	public void strike() {
		super.strike();
		if(super.getTarget() instanceof ResidentialBuilding) {
		//	this.setActive(true);
			((ResidentialBuilding) getTarget()).setGasLevel(((ResidentialBuilding) getTarget()).getGasLevel()+10);
		}
	}

	
}
