package model.infrastructure;

import java.util.ArrayList;

import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.events.SOSListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;
import simulation.Simulator;

import java.util.Random;
public class ResidentialBuilding implements Rescuable, Simulatable {
	private SOSListener emergencyService;
	private Address location;
	private int structuralIntegrity;
	private int fireDamage;
	private int gasLevel;
	private int foundationDamage;
	private ArrayList<Citizen> occupants;
	private Disaster disaster;

	public ResidentialBuilding(Address location) {
		this.location = location;
		this.structuralIntegrity = 100;
		occupants = new ArrayList<Citizen>();

	}
	public ResidentialBuilding(Address location,SOSListener emergencyService) {
		this.emergencyService=emergencyService;
		this.location = location;
		this.structuralIntegrity = 100;
		occupants = new ArrayList<Citizen>();

	}
	public int getStructuralIntegrity() {
		return structuralIntegrity;
	}

	public void setStructuralIntegrity(int structuralIntegrity) {
		if (structuralIntegrity==0) {
			for(int i=0;i<occupants.size();i++) {
				if(occupants.get(i).getLocation()==this.location) {
					(occupants.get(i)).setHp(0);
				}
			}
		}

		if (structuralIntegrity<=0) {
			this.structuralIntegrity = 0;
			for(int i=0;i<occupants.size();i++) {
				if(occupants.get(i).getLocation()==this.location) {
					(occupants.get(i)).setHp(0);

				}
			}
		}
		if(this.structuralIntegrity>0) {
			this.structuralIntegrity=structuralIntegrity;
		}

	}

	public int getFireDamage() {
		return fireDamage;
	}

	public void setFireDamage(int fireDamage) {
		this.fireDamage = fireDamage;
		if(this.fireDamage<0) {
			this.fireDamage=0;
		}

		if(this.fireDamage>100) {
			this.fireDamage=100;
		}
	}
	public int getGasLevel() {
		return gasLevel;
	}

	public void setGasLevel(int gasLevel) {
		this.gasLevel = gasLevel;
		if (this.gasLevel==100) {
			setStructuralIntegrity(0);
		}
	}

	public int getFoundationDamage() {
		return foundationDamage;
	}

	public void setFoundationDamage(int foundationDamage) {
		this.foundationDamage = foundationDamage;
		if (this.foundationDamage>=100) {
			this.structuralIntegrity=0;

		}
		if(structuralIntegrity==0) {
			if(this.getOccupants().size()!=0) {
				for(int i =0;i<this.getOccupants().size();i++) {
					this.getOccupants().get(i).setHp(0);
					this.getOccupants().get(i).setState(CitizenState.DECEASED);
				}
			}
		}
	}

	public Address getLocation() {
		return location;
	}



	public ArrayList<Citizen> getOccupants() {
		return occupants;
	}

	public Disaster getDisaster() {
		return disaster;
	}

	@Override
	public void struckBy(Disaster d) {
		//if(this.foundationDamage==100) {
		//	this.structuralIntegrity=0;
		//	for(int i=0;i<occupants.size();i++) {
		//	 if(occupants.get(i).getLocation()==this.location) {
		//	 (occupants.get(i)).setHp(0);
		//}}}
		this.disaster=d;
		this.getDisaster().setActive(true);
		emergencyService.receiveSOSCall(this);
		if(d instanceof Collapse) {
			if(this.foundationDamage==100) {
				this.structuralIntegrity=0;
				if(structuralIntegrity==0) {
					for(int i =0;i<getOccupants().size();i++) {
						getOccupants().get(i).setHp(0);
					}
				}
			}
			if(d instanceof GasLeak) {
				if(this.gasLevel==100) {
					if(occupants.size()!=0) {
						for(int i=0;i<occupants.size();i++) {
							(occupants.get(i)).setHp(0);
						}
					}
				}

			}}}
	@Override
	public void cycleStep() {
		if (foundationDamage >0) {
			structuralIntegrity-=randomNumberInRange(5,10);
		}
		if(fireDamage>0 && fireDamage<30) {
			structuralIntegrity-=3;
		}
		if(fireDamage>=30&& fireDamage<70) {
			structuralIntegrity-=5;
		}
		if(fireDamage>=70) {
			structuralIntegrity-=7;
		}





	}

	public static int randomNumberInRange(int min,int max) {
		Random r=new Random();
		return r.nextInt((max-min)+1)+ min;

	}


	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}


}
