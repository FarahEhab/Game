package model.disasters;

import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class Fire extends Disaster{
	
	public Fire(int cycle, ResidentialBuilding target) {
		super(cycle,target);	
	}

	@Override
public void cycleStep() {
		ResidentialBuilding b= (ResidentialBuilding)getTarget();
		 b.setFireDamage(b.getFireDamage()+10);
	}
	
	public void strike() {
			super.strike();
		//	this.setActive(true);
				 if(super.getTarget() instanceof ResidentialBuilding) {
					 ResidentialBuilding b=(ResidentialBuilding)super.getTarget();
					 b.getLocation();
					 b.setFireDamage(10);
					 
			 }
	}



	
}
