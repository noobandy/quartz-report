package in.anandm.report.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.anandm.report.services.ReportDataService;
import in.anandm.report.services.ReportStorageService;
import in.anandm.report.services.FSReportStorageService;

@Configuration
public class ReportConfig {
	
	@Value("${report.filePath}")
	private String filePath;

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public ReportDataService getReportDataService() {
		return new ReportDataService();
	}
	
	@Bean
	public ReportStorageService getReportStorageService() {
		return new FSReportStorageService(filePath, getObjectMapper());
	}
}
