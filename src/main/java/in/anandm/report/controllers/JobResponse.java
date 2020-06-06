package in.anandm.report.controllers;

public class JobResponse {

	private String key;
	private JobResponseStatus status;
	
	public JobResponse() {
		super();
		
	}

	public JobResponse(String key, JobResponseStatus status) {
		super();
		this.key = key;
		this.status = status;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public JobResponseStatus getStatus() {
		return status;
	}

	public void setStatus(JobResponseStatus status) {
		this.status = status;
	}
	
	
	
}
