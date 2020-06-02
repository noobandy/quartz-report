package in.anandm.report.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.anandm.report.services.ReportDataService;
import in.anandm.report.services.ReportStorageService;
import in.anandm.report.services.S3ReportStorageService;

@Configuration
public class ReportConfig {

	@Value("${report.filePath}")
	private String filePath;

	@Value("${report.s3.fileName}")
	private String s3FilePath;

	@Value("${report.s3.bucketName}")
	private String bucketName;

	@Value("${report.s3.region}")
	private String region;

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
		return new S3ReportStorageService(getS3Client(), getObjectMapper(), bucketName, s3FilePath);
	}

	@Bean
	public AmazonS3 getS3Client() {

		return AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(new ProfileCredentialsProvider())
				.build();
	}
}
