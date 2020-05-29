package in.anandm.report.services;

import in.anandm.report.model.ReportData;

public interface ReportStorageService {

	void writeReportData(ReportData data) throws StorageException;
}
