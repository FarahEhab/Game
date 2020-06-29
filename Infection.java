package model.disasters;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class Infection extends Disaster{

	public Infection(int cycle, Citizen target) {
		super(cycle,target);

	}

	@Override
	public void cycleStep() {
	Citizen c=(Citizen)getTarget();
		 c.setToxicity(c.getToxicity()+15);
		
	}
	public void strike() {
		super.strike();
	//	this.setActive(true);
			if(super.getTarget() instanceof Citizen) {
				((Citizen) getTarget()).setToxicity(((Citizen) getTarget()).getToxicity()+25);
//				((Citizen) getTarget()).setState(CitizenState.IN_TROUBLE);

			}
		}

	
	}


