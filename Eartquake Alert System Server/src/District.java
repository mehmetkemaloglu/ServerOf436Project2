
public class District {
	String name;
	int earthquakeNumber;
	String pastData;
	public District(String name, int earthquakeNumber, String pastData) {
		super();
		this.name = name;
		this.earthquakeNumber = earthquakeNumber;
		this.pastData = pastData;
	}
	public String getDistrictName() {
		return name;
	}
	public void setDistrictName(String districtName) {
		this.name = districtName;
	}
	public int getDailyCounter() {
		return earthquakeNumber;
	}
	public void setDailyCounter(int dailyCounter) {
		this.earthquakeNumber = dailyCounter;
	}
	public String getPastData() {
		return pastData;
	}
	public void setPastData(String pastData) {
		this.pastData = pastData;
	}
	public void updatePastData(String date) {
		String extraString = date+":"+earthquakeNumber;
		pastData=pastData.substring(0,pastData.length()-1)+"|"+extraString+"'";
		
	}

}
