package model.people;
import java.util.*;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;
import simulation.Simulator;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.events.WorldListener;
import java.lang.Exception;
public class Citizen implements Rescuable, Simulatable {

	private  CitizenState state;
	private Disaster disaster;
	private String name;
	private String nationalID;
	private int age;
	private int hp;
	private int bloodLoss;
	private int toxicity;
	private Address location;
	private  SOSListener emergencyService;
	private WorldListener worldListener;

	public Citizen(Address location) {
		this.location=location;
	}

	public Citizen(Address location, String nationalID, String name, int age,Simulator wl) {

		this.name = name;
		this.nationalID = nationalID;
		this.age = age;
		this.location = location;
		this.state = CitizenState.SAFE;
		this.hp = 100;
		this.emergencyService=(SOSListener)wl;

	}

	public Citizen(Address location, String nationalID, String name, int age) {

		this.name = name;
		this.nationalID = nationalID;
		this.age = age;
		this.location = location;
		this.state = CitizenState.SAFE;
		this.hp = 100;


	}

	public Citizen(Address location2, String nationalID2, String name2, int age2, WorldListener worldListener) {
		this.name = name;
		this.nationalID = nationalID;
		this.age = age;
		this.location = location;
		this.state = CitizenState.SAFE;
		this.hp = 100;
		this.worldListener=worldListener;

	}

//	public Citizen(String string, int i, int j) {
//		// TODO Auto-generated constructor stub
//	}

	public CitizenState getState() {
		return state;
	}

	public void setState(CitizenState state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int getHp() {
		return hp;
	}

	public  void setHp(int hp)  {
		if(hp>100) {
			hp=100;
		}
		if(hp<=0) {
			hp=0;
			this.state=CitizenState.DECEASED;
		}
		this.hp=hp;

	}

	public int getBloodLoss() {
		return bloodLoss;
	}

	public void setBloodLoss(int bloodLoss) {
		this.bloodLoss = bloodLoss;
		if(this.bloodLoss <=0) {
			this.bloodLoss=0;
		}
		if(this.bloodLoss>=100) {
			this.bloodLoss=100;
			this.setHp(0);
		}

	}

	public int getToxicity() {
		return toxicity;
	}

	public void setToxicity(int toxicity) {
		this.toxicity = toxicity;
		if(toxicity<0) {
			toxicity=0;
		}
		if (toxicity>=100) {
			this.setHp(0);
		}

	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public Disaster getDisaster() {
		return disaster;
	}

	public String getNationalID() {
		return nationalID;
	}

	@Override
	public void struckBy(Disaster d) {
		this.disaster=d;
		this.getDisaster().setActive(true);
		if( d instanceof Injury) {
			if(hp==0|| bloodLoss==100 ||toxicity==100) {
				this.state=CitizenState.DECEASED;
			}
			else {
				this.state=CitizenState.IN_TROUBLE;
				if(emergencyService!=null){
					emergencyService.receiveSOSCall(this);
				}}}

		else
			if(d instanceof Infection) {
				if(hp==0|| bloodLoss==100 ||toxicity==100) {
					this.state=CitizenState.DECEASED;
				}
				else {
					this.state=CitizenState.IN_TROUBLE;
					if(emergencyService!=null){
						emergencyService.receiveSOSCall(this);
					}

				}
			}

	}


	@Override
	public  void cycleStep() {
		if(this.hp>0 && this.bloodLoss!=100 && this.toxicity!=100) {
			if(bloodLoss>0 && bloodLoss<30){
				hp=hp-5;
				}
			else {
				if(bloodLoss>=30 && bloodLoss<70) {
					hp=hp-10;
				}
				else {
					if(bloodLoss>=70 && bloodLoss<100) {
						hp=hp-15;
					}}}
				if(hp==0) {
					this.setState(CitizenState.DECEASED);
				}

			if(toxicity>0 && toxicity<30){
				hp=hp-5;}
			else {
				if(toxicity>=30 && toxicity<70) {
					hp=hp-10;
				}
				else {
					if(toxicity>=70 && toxicity<100) {
						toxicity=toxicity+10;
						hp=hp-15;
					}}}}
			if(hp==0) {
				this.setState(CitizenState.DECEASED);
			}
	}


	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}}