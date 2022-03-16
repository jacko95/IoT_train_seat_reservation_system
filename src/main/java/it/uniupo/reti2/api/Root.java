package it.uniupo.reti2.api;

public class Root {
	private String id;
	private String origin;
	//private Map<String, ?> schedule;
	private Schedule schedule;

    
    public String getId() {
    	return this.id;
    }

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

}
