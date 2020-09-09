import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
	
	static List<Area> area_list = new ArrayList<>();

	// actually the whole process is in this method
	static void readInput(String filename) { // attach, detach, data

		BufferedReader file;
		try {
			file = new BufferedReader(new FileReader(filename));
			String line;
			int i = 0;
			while((line = file.readLine()) != null) {
				// System.out.printf("[%s]\n", line);
				// data
				if(line.split(" ").length == 5) {
					// System.out.printf("[data]: %s\n", line);

					String name = line.split(" ")[1];
					int id = searchId(area_list, name);
					Area a = area_list.get(id);
					// System.out.println(a.id);
					
					double temp = Double.parseDouble(line.split(" ")[2]);
					double hum = Double.parseDouble(line.split(" ")[3]);
					double pres = Double.parseDouble(line.split(" ")[4]);
					a.addData(temp, hum, pres);

					// print displays
					for(String d : a.display) {
						if(d.equals("Current")) a.printCurrent();
						if(d.equals("Statistics")) a.printStatistics();
						if(d.equals("Forecast")) a.printForecast();
					}

				}
				// attach & detach
				else {
					String name = line.split(" ")[1];
					String type = line.split(" ")[2];
					Area a = area_list.get(searchId(area_list, name));

					if(line.split(" ")[0].equals("attach")) {
						// System.out.printf("[attach]: %s\n", line);
						a.attachDisplay(type);

					}
					else {
						// System.out.printf("[detach]: %s\n", line);
						a.detachDisplay(type);
					}
				}
			}

			file.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	// search for area name in the given List, and return the index of the object
	static int searchId(List<Area> list, String name) {
		int id = 0;
		for(Area a : list) {
			if(name.equals(a.name))
				return id;
			id++;
		}
		return -1;
	}

	public static void main(String[] args) {

		// System.out.printf("----------------\n\n\n");

		// init, create instance "Asia" & "US"
		area_list.add(new Area(0, "Asia"));
		area_list.add(new Area(1, "US"));

		readInput(args[0]);
	}
}

class Area {
	int id;
	String name;
	List<Double> temperature;
	List<Double> humidity;
	List<Double> pressure;
	List<String> display;

	// class constructor
	Area(int id, String name) {
		this.id = id;
		this.name = name;
		this.temperature = new ArrayList<>();
		this.humidity = new ArrayList<>();
		this.pressure = new ArrayList<>();
		this.display = new ArrayList<>();
	}

	void addData(double temp, double hum, double pres) {
		temperature.add(temp);
		humidity.add(hum);
		pressure.add(pres);
	}
	void attachDisplay(String type) {
		display.add(type);
		//[ Area] will not attach [DisplayType] already attached to it.
	}
	void detachDisplay(String type) {
		display.remove(type);
		// [Area] will not detach [DisplayType] not attached to it.
	}

	void printCurrent() {
		// System.out.println("[C]");

		int index = temperature.size() - 1;
		System.out.printf("Temperature %.1f\n", temperature.get(index));
		System.out.printf("Humidity %.1f\n", humidity.get(index));
		System.out.printf("Pressure %.1f\n", pressure.get(index));
	}
	void printStatistics() {
		// System.out.println("[S]");

		System.out.printf("Temperature");
		for(double n : temperature)
			System.out.printf(" %.1f", n);

		System.out.printf("\nHumidity");
		for(double n : humidity)
			System.out.printf(" %.1f", n);

		System.out.printf("\nPressure");
		for(double n : pressure)
			System.out.printf(" %.1f", n);

		System.out.println();
	}
	void printForecast() { // humidity only
		// System.out.println("[F]");
		
		double hum = humidity.get(humidity.size() - 1);
		if(hum > 0.8)
			System.out.println("Forecast rain.");
		else if(hum < 0.2)
			System.out.println("Forecast sunny.");
		else
			System.out.println("Forecast cloudy.");
	}
}