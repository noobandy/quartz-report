package in.anandm.report.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.anandm.report.model.ReportData;

public class FSReportStorageService implements ReportStorageService {

	private String filePath;
	
	private ObjectMapper mapper;
	
	
	public FSReportStorageService(String filePath, ObjectMapper mapper) {
		super();
		this.filePath = filePath;
		this.mapper = mapper;
	}




	@Override
	public void writeReportData(ReportData data) throws StorageException {
		File outFile = new File(filePath);
		
		try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile))) {
			
			mapper.writeValue(bos, data);
		} catch (IOException e) {
			throw new StorageException(e);
		}
	}

}
