package model.disasters;

import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class Collapse extends Disaster{	
	public Collapse(int cycle,ResidentialBuilding target) {
		super(cycle,target);
	}

	@Override
	public void cycleStep() {
	ResidentialBuilding b= (ResidentialBuilding)getTarget();
	b.setFoundationDamage(b.getFoundationDamage()+10);
		
	}	
	public void strike() {
		super.strike();
		if(super.getTarget() instanceof ResidentialBuilding) {
		//	this.setActive(true);
			((ResidentialBuilding) getTarget()).setFoundationDamage(((ResidentialBuilding) getTarget()).getFoundationDamage()+10);
		
		}
	}
	
}
