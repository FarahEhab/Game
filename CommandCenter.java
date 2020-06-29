package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;

public class CommandCenter implements SOSListener{
	private Simulator engine ;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Rescuable> visibleRescuables;
	
	public CommandCenter() throws Exception {
		this.engine = new Simulator(null);
		this.visibleBuildings= new ArrayList<ResidentialBuilding>();
		this.visibleCitizens=new ArrayList<Citizen>();
		this.emergencyUnits= new ArrayList<Unit>();
		this.visibleRescuables=new ArrayList<Rescuable>();
	}

	public void receiveSOSCall(Rescuable r) {	
		visibleRescuables.add(r);
	}
}
