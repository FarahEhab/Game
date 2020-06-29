package model.units;

import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class DiseaseControlUnit extends MedicalUnit{

	//	
	//	public DiseaseControlUnit(String unitID, Address location, int stepsPerCycle) {
	//
	//		super(unitID, location, stepsPerCycle);
	//
	//	}
	public DiseaseControlUnit(String id, Address location,int stepsPerCycle,WorldListener worldlistener) {
		super(id,location,stepsPerCycle, worldlistener);
	}
	public void treat() {		
		if(this.getTarget() instanceof Citizen) {
		}	
		this.setState(UnitState.TREATING);

		this.getTarget().getDisaster().setActive(false);
		if(((Citizen) this.getTarget()).getToxicity()>0) {
			(((Citizen)this.getTarget())).setToxicity(((Citizen) this.getTarget()).getToxicity()-getTreatmentAmount());
			if(((Citizen) this.getTarget()).getToxicity()==0) {
				((Citizen) this.getTarget()).setState(CitizenState.RESCUED);
			}
		}else {
			heal();
		}
	}


}
