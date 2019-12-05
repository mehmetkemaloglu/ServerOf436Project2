
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Server {
	public static ArrayList<City> cities = new ArrayList<>();
	public static boolean closeHandler = false;
	public static void main(String[] args) throws InterruptedException {
		try {
			System.out.println("Server ip is "+Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date date = new Date();
		LocalDate today = java.time.LocalDate.now();
		String todayString = today.getDayOfMonth()+"-"+today.getMonthValue()+"-"+today.getYear();
		System.out.println("Today is "+todayString);
		File f = new File("./data.txt");
		if (f.exists())
			System.out.println("database exists");
		Scanner init = null;
		try {
			init =new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String dataDate = init.next();
		boolean changeDate = !dataDate.equals(todayString);
		
		while (init.hasNext()) {
			City currentCity = new City(init.next(), init.nextInt(), init.nextInt(), init.next());
			for (int i=0;i<currentCity.numberOfDistricts;i++) {
				District currentDistrict = new District(init.next(), init.nextInt(), init.next());
				currentCity.districts.add(currentDistrict);
			}
			cities.add(currentCity);
		}
		// when the server initialized, if the current date in the data is not today,
		// then update the past data and clear the numbers of today
		if (changeDate) {
			changeData(cities,dataDate);
		}
		Thread t;
		try {
	         t = new HandleClientThread();
	         t.start();
	      } catch (IOException e) {
	         e.printStackTrace();
	    }
		
		
		
		Scanner ui = new Scanner(System.in);
		while(!ui.nextLine().equals("close server"));
		System.out.println("Server will be closed in 5 seconds.");
		closeHandler = true;
		updateData(f, cities, todayString);
		Thread.sleep(5000);
		System.out.println("Server is closed.");
		System.exit(0);

	}
	public static void readData(File f, ArrayList<City> cities) {
		Scanner init = null;
		try {
			init =new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String dataDate = init.next();
		while (init.hasNext()) {
			City currentCity = new City(init.next(), init.nextInt(), init.nextInt(), init.next());
			for (int i=0;i<currentCity.numberOfDistricts;i++) {
				District currentDistrict = new District(init.next(), init.nextInt(), init.next());
				currentCity.districts.add(currentDistrict);
			}
			cities.add(currentCity);
		}
	}
	
	public static void updateData(File f, ArrayList<City> cities, String date) {
		PrintStream out = null;
		try {
			out =new PrintStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(date);
		for (City currentCity:cities) {
			out.println(currentCity.name+" "+currentCity.earthquakeNumber+" "
					+currentCity.districts.size()+" "+currentCity.pastData);
			for (District currentDistrict:currentCity.districts) {
				out.println(currentDistrict.name+" "+currentDistrict.earthquakeNumber+" "
						+currentDistrict.pastData);
			}
		}
	}
	
	public static void changeData(ArrayList<City> cities, String dataDate) {
		for (City currentCity:cities) {
			currentCity.updatePastData(dataDate);
			currentCity.earthquakeNumber=0;
			for (District currentDistrict:currentCity.districts) {
				currentDistrict.updatePastData(dataDate);
				currentDistrict.earthquakeNumber=0;
			}
		}
	}

	
}
