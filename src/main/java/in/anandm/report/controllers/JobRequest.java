package in.anandm.report.controllers;

import java.util.Map;

public class JobRequest {

	private String key;
	private String description;
	private JobType type;
	private JobOperation operation;
	private String cron;
	private Map<String, String> data;
	
	public JobRequest() {
		super();
		
	}

	
	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}

	

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public JobType getType() {
		return type;
	}

	public void setType(JobType type) {
		this.type = type;
	}

	public JobOperation getOperation() {
		return operation;
	}

	public void setOperation(JobOperation operation) {
		this.operation = operation;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
}
