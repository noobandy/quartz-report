package in.anandm.report.controllers;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import in.anandm.report.jobs.ReportJob;

@RestController
public class JobController {

	@Autowired
	private Scheduler scheduler;

	@RequestMapping(path = "/jobs", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> scheduleJob(@RequestBody Map<String, String> requestData)
			throws SchedulerException {

		Long marketId = Long.parseLong(requestData.get("marketId"));
		JobType jobType = JobType.valueOf(requestData.get("jobType"));
		JobOperation operation = JobOperation.valueOf(requestData.get("operation"));

		String cron = requestData.get("cron");

		Map<String, String> result = new HashMap<>();

		switch (operation) {
		case CREATE:
			JobDetail jobDetail = buildJobDetail(jobType, marketId);
			Trigger trigger = buildJobTrigger(jobDetail, cron);
			scheduler.scheduleJob(jobDetail, trigger);

			result.put("name", jobDetail.getKey().getName());
			result.put("group", jobDetail.getKey().getGroup());
			result.put("result", operation.name());
			break;
		case DELETE:

			scheduler.deleteJob(getJobKey(jobType, marketId));

			result.put("name", getJobKey(jobType, marketId).getName());
			result.put("group", getJobKey(jobType, marketId).getGroup());
			result.put("result", operation.name());

			break;
		}

		return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
	}

	private JobKey getJobKey(JobType jobType, long marketId) {
		return new JobKey(String.format("%s_%d", jobType.name(), marketId), "REPORT_JOBS");
	}

	private JobDetail buildJobDetail(JobType jobType, Long marketId) {
		JobDataMap jobDataMap = new JobDataMap();

		jobDataMap.put("marketId", marketId);

		return JobBuilder.newJob(ReportJob.class).withIdentity(getJobKey(jobType, marketId))
				.withDescription(jobType.name()).usingJobData(jobDataMap).storeDurably().build();
	}

	private Trigger buildJobTrigger(JobDetail jobDetail, String cronExpression) {
		return TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobDetail.getKey().getName(), "bre-triggers")
				.withDescription("Report Trigger").startAt(Date.from(Instant.now()))
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
	}

}
