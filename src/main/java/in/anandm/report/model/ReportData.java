package in.anandm.report.model;

import java.util.ArrayList;
import java.util.List;

public class ReportData {

	private List<Item> items = new ArrayList<>();

	public ReportData() {
		super();
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
