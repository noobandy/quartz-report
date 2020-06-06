package in.anandm.report.controllers;

import java.time.Instant;
import java.util.Date;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import in.anandm.report.jobs.ReportJob;

@RestController
public class JobController {
	
	@Autowired
	private Scheduler scheduler;

	@PostMapping("/jobs")
	public @ResponseBody JobResponse scheduleJob(@RequestBody JobRequest request)
			throws SchedulerException {

		
		JobResponse response = new JobResponse();

		switch (request.getOperation()) {
		case CREATE:
			JobDetail jobDetail = buildJobDetail(request);
			Trigger trigger = buildJobTrigger(jobDetail, request.getCron());
			scheduler.scheduleJob(jobDetail, trigger);
			
			response.setKey(request.getKey());
			response.setStatus(JobResponseStatus.SUCCESS);
			break;
		case DELETE:

			scheduler.deleteJob(getJobKey(request));
			
			response.setKey(request.getKey());
			response.setStatus(JobResponseStatus.SUCCESS);
			break;
		default:
			response.setKey(request.getKey());
			response.setStatus(JobResponseStatus.ERROR);
		}

		return response;
	}

	private JobKey getJobKey(JobRequest request) {
		return new JobKey(String.format("%s_%s", request.getType().toString(), request.getKey()), "REPORT_JOBS");
	}

	private JobDetail buildJobDetail(JobRequest request) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.putAll(request.getData());


		return JobBuilder.newJob(ReportJob.class).withIdentity(getJobKey(request))
				.withDescription(request.getDescription()).usingJobData(jobDataMap).storeDurably().build();
	}

	private Trigger buildJobTrigger(JobDetail jobDetail, String cronExpression) {
		return TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobDetail.getKey().getName(), "REPORT_TRIGGERS")
				.withDescription(jobDetail.getDescription()).startAt(Date.from(Instant.now()))
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
	}

}
