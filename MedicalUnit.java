package model.units;

import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

abstract public class MedicalUnit extends Unit{
	private int healingAmount=10;
	private int treatmentAmount=10;


	public int getTreatmentAmount() {
		return treatmentAmount;
	}

	//	public MedicalUnit(String unitID, Address location, int stepsPerCycle) {
	//
	//		super(unitID, location, stepsPerCycle);
	//		healingAmount = 10;
	//		treatmentAmount = 10;
	//
	//	}

	public MedicalUnit(String id, Address location,int stepsPerCycle,WorldListener worldlistener) {
		super(id,location,stepsPerCycle,worldlistener);
		healingAmount = 10;
		treatmentAmount = 10;
	}
	public void heal() {
		if(((Citizen) getTarget()).getHp()<100) {
			((Citizen) getTarget()).setState(CitizenState.RESCUED);
			((Citizen) getTarget()).setHp(((Citizen) getTarget()).getHp()+healingAmount);

		}
		if(((Citizen) getTarget()).getHp()==100 || ((Citizen) getTarget()).getState()==CitizenState.DECEASED) {

			super.jobsDone();
		}


	}


}
