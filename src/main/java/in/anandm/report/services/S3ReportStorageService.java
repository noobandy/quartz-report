package in.anandm.report.services;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.anandm.report.model.ReportData;

public class S3ReportStorageService implements ReportStorageService {

	private AmazonS3 s3;
	private ObjectMapper objectMapper;
	private String bucketName;
	private String fileName;
	


	public S3ReportStorageService(AmazonS3 s3, ObjectMapper objectMapper, String bucketName, String fileName) {
		super();
		this.s3 = s3;
		this.objectMapper = objectMapper;
		this.bucketName = bucketName;
		this.fileName = fileName;
	}



	@Override
	public void writeReportData(ReportData data) throws StorageException {
		try {
			s3.putObject(bucketName, fileName, objectMapper.writeValueAsString(data));
		} catch (SdkClientException|JsonProcessingException e) {
			throw new StorageException(e);
		}
	}

}
