import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Threadception extends Thread{
	Socket server;

	public Threadception(Socket server) {
		super();
		this.server = server;
	}
	public void run() {
		try {
			
			BinarySemaphore mutex = new BinarySemaphore(true);
			BinarySemaphore mutex2 = new BinarySemaphore(true);

			System.out.println("Just connected to " + server.getRemoteSocketAddress());
			Scanner in = new Scanner(server.getInputStream());
			PrintStream out = new PrintStream(server.getOutputStream());
			String input = in.nextLine();
			Scanner init = new Scanner(input);
			String inputSignature = init.next();
			System.out.println(server.getRemoteSocketAddress()+" sent data: "+input);
			

			if (inputSignature.equals("update")) {
				String cityName = init.next();
				String districtName = init.next();
				for (City currentCity:Server.cities) {
					if (cityName.equals(currentCity.name)) {
						mutex.P();
						currentCity.earthquakeNumber++;
						mutex.V();
						for (District currentDistrict:currentCity.districts) {
							if (districtName.equals(currentDistrict.name)) {
								mutex2.P();
								currentDistrict.earthquakeNumber++;
								mutex2.V();
							}

						}
					}   		
				}

			}
			else if (inputSignature.equals("cityList")) {
				for (City currentCity:Server.cities) {
					out.println(currentCity.name+" "+currentCity.earthquakeNumber);
				}

			}
			else if (inputSignature.equals("districtList")) {
				String cityName = init.next();
				for (City currentCity:Server.cities) {
					if (cityName.equals(currentCity.name)) {
						for (District currentDistrict:currentCity.districts) {
							out.println(currentDistrict.name+" "+currentDistrict.earthquakeNumber);
						}
					}   		
				}

			}
			else if (inputSignature.equals("districtDetails")) {
				String cityName = init.next();
				String districtName = init.next();
				for (City currentCity:Server.cities) {
					if (cityName.equals(currentCity.name)) {
						for (District currentDistrict:currentCity.districts) {
							if (districtName.equals(currentDistrict.name)) {
								out.println(currentDistrict.earthquakeNumber+" "+currentDistrict.pastData);
							}

						}
					}   		
				}

			}
			System.out.println(server.getRemoteSocketAddress()+" is handled");

			server.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
