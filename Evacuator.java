package model.units;

import java.util.ArrayList;

import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Evacuator extends PoliceUnit{

	//	public Evacuator(String unitID, Address location, int stepsPerCycle, int maxCapacity) {
	//
	//		super(unitID, location, stepsPerCycle,maxCapacity);
	//
	//	}



	public Evacuator(String id, Address location,int stepsPerCycle,WorldListener worldlistener,int maxCapacity) {
		super(id,location,stepsPerCycle,worldlistener,maxCapacity);
	}

	@Override
	public void cycleStep() {
		if(getDistanceToBase()>0) {
			((Evacuator)this).setDistanceToBase(getDistanceToBase()-getStepsPerCycle());
		}else {
			if(getPassengers().size()!=0) {
				getWorldListener().assignAddress(((Evacuator)this), 0, 0);
				while(!getPassengers().isEmpty()) {
					Citizen c = getPassengers().remove(0);
					c.getWorldListener().assignAddress(c, 0, 0);
					c.setState(CitizenState.RESCUED);
				}

				setDistanceToTarget(getTarget().getLocation().getX()+getTarget().getLocation().getY());
				if(this.getDistanceToBase()<=0) {
					this.setDistanceToBase(0);
				}
			}else {
				super.cycleStep();
			}
		}
	}

	public void treat() {

		ResidentialBuilding x = (ResidentialBuilding) this.getTarget();

		this.setState(UnitState.TREATING);
		if(this.getTarget()!=null) {
			if(x.getStructuralIntegrity()==0) {
				for(int i =0;i<x.getOccupants().size();i++) {
					x.getOccupants().get(i).setHp(0);
				}
			}else {
				for(int i =0;i<x.getOccupants().size() && getMaxCapacity()>getPassengers().size();i++) {
					if(x.getOccupants().get(i).getState()!=CitizenState.DECEASED) {
					Citizen c = x.getOccupants().get(0);

					c=x.getOccupants().remove(0);
					getPassengers().add(c);

					setDistanceToBase(getTarget().getLocation().getX()+getTarget().getLocation().getY());


				}
			}
			if(this.getPassengers().size()==0 && ((ResidentialBuilding) this.getTarget()).getOccupants().size()==0) {
				jobsDone();


			}	
		}
	}
}


//		if((this.getLocation().getX()==this.getTarget().getLocation().getX())&&(this.getLocation().getY()==this.getTarget().getLocation().getY())) {
//			this.getTarget().getDisaster().setActive(false);
//			while(!((ResidentialBuilding) this.getTarget()).getOccupants().isEmpty()) {

//				
//				if(this.getTarget().getLocation().getX()!=0 && this.getTarget().getLocation().getY()!=0){
//					getWorldListener().assignAddress(this, 0, 0);
//					
//				}
//				if(this.getTarget().getLocation().getX()==0 && this.getTarget().getLocation().getY()==0) {
//					while(!this.getPassengers().isEmpty())
//					this.getPassengers().remove(0);
//					this.getPassengers().get(0).setState(CitizenState.RESCUED);
//				}


//					this.getTarget().getDisaster().setActive(false);
//						if(this.getLocation()==this.getTarget().getLocation()) {
//							this.cycleStep();
//							ResidentialBuilding b=(ResidentialBuilding)this.getTarget();
//							for(int i=0;i<this.getMaxCapacity()&& !b.getOccupants().isEmpty();i++) {
//								Citizen c=b.getOccupants().get(i);
//								b.getOccupants().remove(i);
//								this.getPassengers().add(c);
//
//				}
//					for(int i=0;i<this.getMaxCapacity();i++) {
//						b.getOccupants().remove(i);
//						}
//			}
//				while(!((ResidentialBuilding) this.getTarget()).getOccupants().isEmpty()) {
//					for(int i =0;i<this.getMaxCapacity();i++) {
//						this.getPassengers().add(((ResidentialBuilding) this.getTarget()).getOccupants().get(i));
//						this.getPassengers().get(i).setState(CitizenState.RESCUED);
//					}
//					while(!getPassengers().isEmpty()) {
//			Citizen tmp = this.getPassengers().remove(0);
//						getWorldListener().assignAddress(this.getPassengers().remove(0), 0, 0);
//					}
//
//					}					
//				}
//				}
//
public void respond(Rescuable r) {
	if (this instanceof Evacuator) {
		if(this.getPassengers().size()==this.getMaxCapacity()) {
			getWorldListener().assignAddress(this, 0, 0);
		}
		
	}
}}


