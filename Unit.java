package model.units;

import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

abstract public class Unit implements Simulatable{
	private String unitID ;
	private UnitState state=UnitState.IDLE;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;
	private int distanceToBase;





	public int getDistanceToBase() {
		return distanceToBase;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
		if(this.distanceToTarget<=0) {
			if(this.getTarget()!=null)
				getWorldListener().assignAddress(this, this.getTarget().getLocation().getX(), this.getTarget().getLocation().getY());
			this.distanceToTarget=0;
		}
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}					

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public Rescuable getTarget() {
		return target;
	}

	public Address getLocation() {
		return location;
	}

	public void setDistanceToBase(int distanceToBase) {
		this.distanceToBase = distanceToBase;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public UnitState getState() {
		return state;

	}
	public void setState(UnitState state) {
		this.state = state;
	}

	public String getUnitID() {
		return unitID;
	}

	public Unit(String id,Address location, int stepsPerCycle) {
		this.unitID=id;
		this.location=location;
		this.stepsPerCycle=stepsPerCycle;
	}

	public Unit(String id,Address location, int stepsPerCycle,WorldListener worldlistener) {
		this.unitID=id;
		this.location=location;
		this.stepsPerCycle=stepsPerCycle;
		this.worldListener=worldlistener;
	}

	public String toString() {
		return "location:"+this.location+"unitID:"+this.unitID+"stepPerCycle:"+this.stepsPerCycle;
	}

	public void cycleStep() {
		
	if(this.getState()!=UnitState.IDLE) {
		
		if(this.distanceToTarget>0) {
			this.setDistanceToTarget(distanceToTarget-stepsPerCycle);
			if(this.distanceToTarget<=0) {
				distanceToTarget=0;
			}

		}else {
			if(this.distanceToTarget<=0) {	
				this.setDistanceToTarget(0);
				if(this.getTarget()!=null) {
					if(this.getTarget().getDisaster()!=null)
						target.getDisaster().setActive(false);
				}
				this.setState(UnitState.TREATING);
				this.treat();
			}
		}
	}

		//		if((this.getTarget().getLocation().getX()==this.getLocation().getX())&&this.getLocation().getY()==this.getTarget().getLocation().getY()) {
		//			this.treat();
		//		}
		//		if(this instanceof Evacuator) {
		//			if(this.getState() == UnitState.RESPONDING) {
		//				if(distanceToTarget - stepsPerCycle == 0) {
		//					((Evacuator)this).getWorldListener().assignAddress(((Evacuator)this), getTarget().getLocation().getX(), getTarget().getLocation().getY());
		//					if((((Evacuator)this).getTarget().getLocation().getX()==((Evacuator)this).getLocation().getX())&&((Evacuator)this).getLocation().getY()==((Evacuator)this).getTarget().getLocation().getY()) {
		//						((Evacuator)this).treat();
		//						((Evacuator)this).setState(UnitState.TREATING);
		//					}
		//				}else {
		//					if(distanceToTarget - stepsPerCycle <=0) {
		//						((Evacuator)this).setDistanceToTarget(0); 
		//						((Evacuator)this).setDistanceToBase(((Evacuator)this).getDistanceToBase()+distanceToTarget);
		//
		//					}else {
		//						if(distanceToTarget - stepsPerCycle >0) {
		//							((Evacuator)this).setDistanceToTarget(distanceToTarget-stepsPerCycle);
		//							((Evacuator)this).setDistanceToBase(((Evacuator)this).getDistanceToBase()+stepsPerCycle);
		//						}
		//					}
		//				}
		//			}else {
		//				if(this.getState() == UnitState.TREATING) {
		//					if(((Evacuator)this).getDistanceToBase()==0) {
		//						((Evacuator)this).getWorldListener().assignAddress(((Evacuator)this), 0, 0);		//Since it's at the base
		//						if((((Evacuator)this).getLocation().getX()==((Evacuator)this.getTarget()).getLocation().getX())&&((Evacuator)this).getLocation().getY()==((Evacuator)this).getTarget().getLocation().getY()) {
		//							((Evacuator)this).treat();
		//						}
		//					}else {
		//						if(((Evacuator)this).getDistanceToBase()-stepsPerCycle >0) {
		//							((Evacuator)this).setDistanceToBase(((Evacuator)this).getDistanceToBase()-stepsPerCycle);
		//							((Evacuator)this).setDistanceToTarget(this.distanceToTarget+stepsPerCycle);
		//						}else {
		//							if(((Evacuator)this).getDistanceToBase()-stepsPerCycle<=0) {
		//								((Evacuator)this).setDistanceToBase(0);
		//								((Evacuator)this).setDistanceToTarget(distanceToBase+this.distanceToTarget);
		//							}
		//						}
		//					}
		//				}else {
		//					if(((Evacuator)this).getState() == UnitState.RESPONDING) {
		//						if(distanceToTarget - stepsPerCycle == 0) {
		//							getWorldListener().assignAddress(((Evacuator)this), ((Evacuator)this.getTarget()).getLocation().getX(), ((Evacuator)this.getTarget()).getLocation().getY());
		//							if(((Evacuator)this.getTarget()).getLocation().getX()==((Evacuator)this).getLocation().getX()&&((Evacuator)this.getTarget()).getLocation().getY()==((Evacuator)this).getLocation().getY()) {
		//								((Evacuator)this).treat();
		//								this.setState(UnitState.TREATING);
		//							}
		//						}
		//					}
		//				}
		//			}
		//		}

	}

	public void heal() {

	}

	public void treat() {
		if(this.getState().equals(UnitState.TREATING)) {					
			if(this.getTarget().getDisaster().isActive()) {
				this.getTarget().getDisaster().setActive(false);
			}
		}

		//		if(this.getTarget().getDisaster().isActive()) {
		//			this.getTarget().getDisaster().setActive(false);
		//		}
	}
	public void jobsDone() {
		if(this.getTarget() instanceof Citizen) {
			if(this.getState()!=UnitState.IDLE) {
				if((((Citizen) this.getTarget()).getState().equals(CitizenState.DECEASED)||((Citizen) this.getTarget()).getState().equals(CitizenState.RESCUED))&&(this.getTarget().getDisaster().isActive()==false)) {
					//				this.setState(UnitState.IDLE);
				}
			}
			//		}else {
			//			if(this.getTarget().getDisaster().isActive()==false) {
			//			}
			//
			//		
			this.target=null;
			this.setState(UnitState.IDLE);
		}
	}

	public void respond(Rescuable r) {
		if(this.getTarget()!=null) {
			if(this instanceof MedicalUnit ) {
				if(!(((Citizen) this.getTarget()).getState().equals(CitizenState.RESCUED))) {
					this.getTarget().struckBy(this.getTarget().getDisaster());
				}
			}else {
				this.getTarget().struckBy(this.getTarget().getDisaster());
			}
		}
		target=r;
		this.setState(UnitState.RESPONDING);
		int x1=this.getLocation().getX();
		int x2=r.getLocation().getX();
		int y1=this.getLocation().getY();
		int y2=r.getLocation().getY();
		int x3=x2-x1;
		int y3=y2-y1;
		if(x3<=0) {
			x3=x3*-1;
		}
		if(y3<=0) {
			y3=y3*-1;
		}
		 distanceToTarget=x3+y3;
			
		//			}	
	}
}
////		else {
//			if(this.getTarget()!=null && this.getTarget().getDisaster()!=null) 
//				this.target.getDisaster().setActive(true);
//			target=r;
//			if(this.getLocation().equals(r.getLocation()))
//				this.setState(UnitState.TREATING);
//			else {
//				this.setState(UnitState.RESPONDING);
//				int x1=this.getLocation().getX();
//				int x2=this.getTarget().getLocation().getX();
//				int y1=this.getLocation().getY();
//				int y2=this.getTarget().getLocation().getY();
//				distanceToTarget=Math.abs(x2-x1)+Math.abs(y2-y1);
//				this.setDistanceToTarget(distanceToTarget);			
//			}
////		}
//	}
//}













//			}

//		else {
//			if(this.state==UnitState.RESPONDING) {
//				if(this.getTarget()!=r) {
//					this.cycleStep();
//					Rescuable s=this.getTarget();
//					s.getDisaster().setActive(true);
//					int x1=this.getLocation().getX();
//					int x2=r.getLocation().getX();
//					int y1=this.getLocation().getY();
//					int y2=r.getLocation().getY();
//					distanceToTarget=(Math.abs(x2-x1)+Math.abs(y2-y1));
//					this.setDistanceToTarget(distanceToTarget);
//				}
//	}
//	}	
//	}}}
//		if(this.state==UnitState.IDLE) {
//			int x1=this.getLocation().getX();
//			int x2=this.getTarget().getLocation().getX();
//			int y1=this.getLocation().getY();
//			int y2=this.getTarget().getLocation().getY();
//			distanceToTarget=(x2-x1)+(y2-y1);
//			if(distanceToTarget - stepsPerCycle==0) {
//				getWorldListener().assignAddress(this, x2, y2);
//				this.treat();
//				this.setState(UnitState.TREATING);
//			}
//		}
//		if(this.state==UnitState.IDLE){
//			int x1=this.getLocation().getX();
//			int x2=this.getTarget().getLocation().getX();
//			int y1=this.getLocation().getY();
//			int y2=this.getTarget().getLocation().getY();
//			distanceToTarget=(x2-x1)+(y2-y1);
//			this.setDistanceToTarget(distanceToTarget);
//			if(distanceToTarget - stepsPerCycle>0) {
//				this.setDistanceToTarget(distanceToTarget);
//				getWorldListener().assignAddress(this, x2, y2);
//				this.setState(UnitState.RESPONDING);
//			}
//		}
//			if(this.state==UnitState.RESPONDING) {
//				int x1=this.getLocation().getX();
//				int x2=this.getTarget().getLocation().getX();
//				int y1=this.getLocation().getY();
//				int y2=this.getTarget().getLocation().getY();
//				distanceToTarget=(x2-x1)+(y2-y1);
//				if(distanceToTarget - stepsPerCycle == 0) {
//					this.treat();
//					this.setState(UnitState.TREATING);
//				}
//			}
//				if(this.state==UnitState.RESPONDING) {
//
//					int x1=this.getLocation().getX();
//					int x2=this.getTarget().getLocation().getX();
//					int y1=this.getLocation().getY();
//					int y2=this.getTarget().getLocation().getY();
//					distanceToTarget=(x2-x1)+(y2-y1);
//					this.setDistanceToTarget(distanceToTarget);
//					if(distanceToTarget - stepsPerCycle>0) {
//						this.setDistanceToTarget(distanceToTarget);
//						getWorldListener().assignAddress(this, x2, y2);
//						this.setState(UnitState.RESPONDING);
//					}
//				}


//			if(!this.getTarget().equals(r)){
//				this.getTarget().getDisaster().setActive(true);
//				r=this.getTarget();
//				if(this.getTarget()==r) {
//					this.cycleStep();	
//				}
//			}else {
//				if(this.getTarget().equals(r))
//					this.cycleStep();
//	
//this.cycleStep();

//	if(this instanceof MedicalUnit) {
//		if(!this.getTarget().equals(r)) {
//		if(this.getState()==UnitState.TREATING) {
//				this.getTarget().getDisaster().setActive(false);
//				this.setState(UnitState.RESPONDING);
//				r=this.getTarget();
//				getWorldListener().assignAddress(this, r.getLocation().getX(), r.getLocation().getY());
//				if(this.getLocation()==r.getLocation()) {
//					this.treat();
//					this.setState(UnitState.TREATING);
//			}else {
//					this.setState(UnitState.RESPONDING);
//				}
//			}
//		}else {
//if(this.getTarget().equals(r)) {
//			this.cycleStep();


//	}
//		}
//	}
//}


