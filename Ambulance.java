package model.units;

import model.events.SOSResponder;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit{
	//	public Ambulance(String unitID, Address location, int stepsPerCycle) {
	//
	//		super(unitID, location, stepsPerCycle);
	//
	//	}


	public Ambulance(String id, Address location,int stepsPerCycle,WorldListener worldlistener) {
		super(id,location,stepsPerCycle, worldlistener);
	}

	public void treat() {

		if(this.getTarget()!=null) {

			if(this.getTarget() instanceof Citizen) {
				this.getTarget().getDisaster().setActive(false);
				
				if(((Citizen) this.getTarget()).getBloodLoss()>0) {
					(((Citizen)this.getTarget())).setBloodLoss(((Citizen) this.getTarget()).getBloodLoss()-getTreatmentAmount());
				}
				
				if(((Citizen) this.getTarget()).getBloodLoss()<=0) {
					((Citizen) this.getTarget()).setState(CitizenState.RESCUED);
					this.heal();
					
				}
			}
		}

	}}
