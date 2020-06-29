package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;

public class Simulator implements WorldListener{
	private int currentCycle=0;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][] world;
	private SOSListener emergencyService;

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
		if(emergencyService!=null) {
			emergencyService.receiveSOSCall((Rescuable) this);
		}
	}


	public Simulator(SOSListener emergencyService) throws Exception {
		this.world = new Address[10][10];
		for(int i = 0;i<world.length;i++) {
			for(int j =0;j<world.length;j++) {
				world[i][j] = new Address(i, j);
			}
		}

		buildings =	new ArrayList<ResidentialBuilding>();
		emergencyUnits = new ArrayList<Unit>();
		citizens  =	new ArrayList<Citizen>();
		plannedDisasters= new ArrayList<Disaster>();
		executedDisasters= new ArrayList<Disaster>();
		this.emergencyService=emergencyService;
		this.loadBuildings("buildings.csv");
		this.loadCitizens("citizens.csv");
		this.loadDisasters("disasters.csv");
		this.loadUnits("units.csv");

		for(int i =0;i<citizens.size();i++) {
			citizens.get(i).setEmergencyService(emergencyService);
		}
		for(int i =0;i<buildings.size();i++) {
			buildings.get(i).setEmergencyService(emergencyService);
		}

		for(int i =0;i<emergencyUnits.size();i++) {
			emergencyUnits.get(i).setWorldListener(this);
		}

		for(int i =0;i<citizens.size();i++) {
			citizens.get(i).setWorldListener(this);
		}
	}


	public ArrayList<Unit> getEmergencyUnits() {
		return emergencyUnits;
	}


	private void loadDisasters(String filePath) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get(filePath));
		for (int i =  0;i<lines.size();i++) {
			String[] tmp=lines.get(i).split(",");
			if(tmp[1].equals("INF")) {
				plannedDisasters.add(new Infection(Integer.parseInt(tmp[0]), getCitizen(tmp[2])));
			}else if (tmp[1].equals("INJ")) {
				plannedDisasters.add(new Injury(Integer.parseInt(tmp[0]), getCitizen(tmp[2])));
			}else if (tmp[1].equals("FIR")) {
				plannedDisasters.add(new Fire(Integer.parseInt(tmp[0]), getBuilding(world[Integer.parseInt(tmp[2])] [Integer.parseInt(tmp[3])])));
			}else {
				plannedDisasters.add(new GasLeak(Integer.parseInt(tmp[0]), getBuilding(world[Integer.parseInt(tmp[2])] [Integer.parseInt(tmp[3])])));
			}
		}

	}

	private Citizen getCitizen(String id) {
		for(int i=0;i<citizens.size();i++) {
			if(id.equals(citizens.get(i).getNationalID())) {
				return citizens.get(i);
			}
		}
		return null;
	}
	private ResidentialBuilding getBuilding(Address a) {
		for(int i=0;i<buildings.size();i++) {
			if(buildings.get(i).getLocation().getX()==a.getX()&&buildings.get(i).getLocation().getY()==a.getY()) {
				return buildings.get(i);
			}
		}
		return null;
	}

	private void loadCitizens(String path) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();

		while (line != null) {

			String[] info = line.split(",");
			int x = Integer.parseInt(info[0]);
			int y = Integer.parseInt(info[1]);
			String id = info[2];
			String name = info[3];
			int age = Integer.parseInt(info[4]);
			Citizen c=new Citizen(world[x][y], id, name, age);
			c.setEmergencyService(emergencyService);
			citizens.add(c);

			line = br.readLine();

		}
		for(int i =0;i<citizens.size();i++) {
			citizens.get(i).setWorldListener(this);
		}
		br.close();
	}

	private void loadBuildings(String path) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();

		while (line != null) {

			String[] info = line.split(",");
			int x = Integer.parseInt(info[0]);
			int y = Integer.parseInt(info[1]);
			ResidentialBuilding b= new ResidentialBuilding(world[x][y]);
			b.setEmergencyService(emergencyService);
			buildings.add(b);

			line = br.readLine();

		}
		br.close();
	}

	private void loadUnits(String path) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();

		while (line != null) {

			String[] info = line.split(",");
			String id = info[1];
			int steps = Integer.parseInt(info[2]);

			switch (info[0]) {

			case "AMB":
				Ambulance ambulance=new Ambulance(id, world[0][0], steps, null);
				ambulance.setWorldListener(this);
				emergencyUnits.add(ambulance);
				break;

			case "DCU":
				DiseaseControlUnit diseasecontrolunit=(new DiseaseControlUnit(id, world[0][0], steps,null));
				diseasecontrolunit.setWorldListener(this);
				emergencyUnits.add(diseasecontrolunit);
				break;

			case "EVC":
				Evacuator evacuator=new Evacuator(id, world[0][0], steps, null, Integer.parseInt(info[3]));
				evacuator.setWorldListener(this);
				emergencyUnits.add(evacuator);
				break;

			case "FTK":
				FireTruck firetruck=new FireTruck(id, world[0][0], steps, null);
				firetruck.setWorldListener(this);
				emergencyUnits.add(firetruck);
				break;

			case "GCU":
				GasControlUnit gascontrolunit=new GasControlUnit(id, world[0][0], steps, null);
				gascontrolunit.setWorldListener(this);
				emergencyUnits.add(gascontrolunit);
				break;

			}

			line = br.readLine();
			for(int i =0;i<emergencyUnits.size();i++) {
				emergencyUnits.get(i).setWorldListener(this);
			}
		}

		br.close();
	}

	
	@Override
	public void assignAddress(Simulatable sim, int x, int y) {
		if(sim instanceof Citizen) {
			Address a = world[x][y];
			Citizen c = ((Citizen)sim);
			c.setLocation(a);
		}
		else {
			if(sim instanceof Unit) {
				Address a = world[x][y];
				Unit u =((Unit)sim);
				u.setLocation(a);
			}
		}

	}


	public void nextCycle() {
		this.currentCycle++;
		for(int i =0;i<plannedDisasters.size();i++) {
			if(plannedDisasters.get(i).getTarget() instanceof ResidentialBuilding) {
				if(plannedDisasters.get(i) instanceof Collapse) {
					plannedDisasters.get(i).getTarget().getDisaster().setActive(false);
					((ResidentialBuilding) plannedDisasters.get(i).getTarget()).setFireDamage(0);
					//	plannedDisasters.get(i).strike();
					plannedDisasters.get(i).getTarget().struckBy(plannedDisasters.get(i));
				}


				if(plannedDisasters.get(i) instanceof Fire) {
					if(plannedDisasters.get(i).getTarget().getDisaster() instanceof GasLeak) {
						if(((ResidentialBuilding) plannedDisasters.get(i).getTarget()).getGasLevel()==0) {
							//	plannedDisasters.get(i).strike();
							plannedDisasters.get(i).getTarget().struckBy(plannedDisasters.get(i));
							executedDisasters.add(plannedDisasters.get(i));
						}

						else {
							if(((ResidentialBuilding) plannedDisasters.get(i).getTarget()).getGasLevel()>0 && ((ResidentialBuilding) plannedDisasters.get(i).getTarget()).getGasLevel()<70) {
								Disaster fireDamage = new Collapse(currentCycle, (ResidentialBuilding) plannedDisasters.get(i).getTarget());
								fireDamage.strike();
								plannedDisasters.get(i).getTarget().struckBy(fireDamage);
								executedDisasters.add(fireDamage);

							}else {
								if(((ResidentialBuilding) plannedDisasters.get(i).getTarget()).getGasLevel()>=70) {
									((ResidentialBuilding) plannedDisasters.get(i).getTarget()).setStructuralIntegrity(0);
									if(((ResidentialBuilding) plannedDisasters.get(i).getTarget()).getStructuralIntegrity()==0) {
										((ResidentialBuilding) plannedDisasters.get(i).getTarget()).getOccupants().get(i).setHp(0);
									}
								}	
							}
						}
					}	


				}


				if(plannedDisasters.get(i) instanceof GasLeak) {
					if(plannedDisasters.get(i).getTarget().getDisaster() instanceof Fire) {
						plannedDisasters.get(i).getTarget().getDisaster().setActive(false);
						Collapse gasDamage = new Collapse(currentCycle, (ResidentialBuilding) plannedDisasters.get(i).getTarget());
						((ResidentialBuilding) plannedDisasters.get(i).getTarget()).setFireDamage(0);
						gasDamage.strike();
						plannedDisasters.get(i).getTarget().struckBy(gasDamage);
						executedDisasters.add(gasDamage);

					}
				}
			}
//			if(plannedDisasters.get(i) instanceof Injury) {
//				if(plannedDisasters.get(i).getStartCycle()==currentCycle) {
//					plannedDisasters.get(i).strike();
//				}else {
//					plannedDisasters.get(i).setActive(false);
//				}
//			}
			plannedDisasters.get(i).strike();
			plannedDisasters.remove(i);
		}

		for(int i =0;i<emergencyUnits.size();i++) {
			
			emergencyUnits.get(i).cycleStep();
		}
		for(int i=0;i<executedDisasters.size();i++) {
			Disaster p = executedDisasters.get(i);
			if(p.isActive() && currentCycle>p.getStartCycle()) {
				
				executedDisasters.get(i).cycleStep();
			}
		}
		for(int i =0;i<buildings.size();i++) {
//			if(this.citizens.get(i).getDisaster()!=null&&this.buildings.get(i).getDisaster().isActive()) {
				buildings.get(i).cycleStep();
		//	}
		


			//			if(((ResidentialBuilding) plannedDisasters.get(i).getTarget()).getFireDamage()==100) {
			//				Disaster fireDamage = new Collapse(currentCycle, (ResidentialBuilding) plannedDisasters.get(i).getTarget());
			//				fireDamage.strike();
			//				plannedDisasters.get(i).getTarget().struckBy(fireDamage);
			//				executedDisasters.add(fireDamage);
			////				break;
			//}
		}
		for(int i =0;i<citizens.size();i++) {
//			if(this.citizens.get(i).getDisaster()!=null&&this.citizens.get(i).getDisaster().isActive()) {
				citizens.get(i).cycleStep();
			}
			
		}
	


	public int calculateCasualties() {
		int dead = 0;
		for(int i =0;i<this.citizens.size();i++) {
			if(this.citizens.get(i).getState().equals(CitizenState.DECEASED)) {
				dead=dead+1;
			}
			else {
				dead=dead+0;
			}

		}
		return dead; }

	public boolean checkGameOver() {
		if(!plannedDisasters.isEmpty()) {
			return false;
		}
		if(!executedDisasters.isEmpty()) {
			return false;
		}
		for(int i =0;i<emergencyUnits.size();i++) {
			if(emergencyUnits.get(i).getState()==UnitState.TREATING || emergencyUnits.get(i).getState()!=UnitState.IDLE){
				return false;
			}

		}
		return true;

	}}
