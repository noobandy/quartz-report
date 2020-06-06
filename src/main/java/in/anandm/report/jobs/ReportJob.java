package in.anandm.report.jobs;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import in.anandm.report.services.ReportDataService;
import in.anandm.report.services.ReportStorageService;
import in.anandm.report.services.StorageException;

@Component
public class ReportJob extends QuartzJobBean {

	@Autowired
	private ReportDataService reportDataService;
	
	@Autowired
	private ReportStorageService reportStorageService;
	
	
	public ReportJob() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap dataMap = context.getMergedJobDataMap();
		
		
		try {
			reportStorageService.writeReportData(reportDataService.getReportData(dataMap.getWrappedMap()));
		} catch (StorageException e) {
			throw new JobExecutionException(e);
		}
	}

	

}
