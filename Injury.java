package model.disasters;

import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class Injury extends Disaster{
	public Injury(int cycle, Citizen target) {
		super(cycle,target);

	}

	@Override
	public void cycleStep() {
			Citizen c=(Citizen)getTarget();
			c.setBloodLoss(c.getBloodLoss()+10);
		
	}

	public void strike() {
	//	if(!this.isActive()) {
	//	}
		//			super.strike();		
		//	this.setActive(true);

		if(this.getTarget() instanceof Citizen) {
			Citizen c=(Citizen)this.getTarget();
			c.getLocation();
			c.setBloodLoss(30);
			c.setState(CitizenState.IN_TROUBLE);

		}
	}

}
