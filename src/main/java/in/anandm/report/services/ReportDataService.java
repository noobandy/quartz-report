package in.anandm.report.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.anandm.report.model.Item;
import in.anandm.report.model.ReportData;

public class ReportDataService {

	
	public ReportData getReportData(Map<String, Object> params) {
		
		int start = Integer.parseInt(params.get("start").toString());
		
		ReportData data = new ReportData();
		List<Item> items = new ArrayList<>();
		for(int i = start ; i < 10; i++) {
			items.add(new Item(i));
		}
		
		data.setItems(items);
		
		return data;
	}
}
