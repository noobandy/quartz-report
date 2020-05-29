package in.anandm.report.services;

import java.util.ArrayList;
import java.util.List;

import in.anandm.report.model.Item;
import in.anandm.report.model.ReportData;

public class ReportDataService {

	
	public ReportData getReportData(long marketId) {
		ReportData data = new ReportData();
		List<Item> items = new ArrayList<>();
		for(int i =0 ; i < 10; i++) {
			items.add(new Item(i));
		}
		
		data.setItems(items);
		
		return data;
	}
}
