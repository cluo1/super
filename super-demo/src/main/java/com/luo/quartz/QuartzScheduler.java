package com.luo.quartz;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * 类名称：QuartzScheduler
 * 类描述：任务调度处理：
 * @version </pre>
 */
@Component
public class QuartzScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzScheduler.class);
    /**
     * 任务调度
     */
    @Autowired
    private Scheduler scheduler;

    /**
     * 获取Job信息
     *
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组名
     */
    public String getJobInfo(String triggerName, String triggerGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time：%s，state：%s", cronTrigger.getCronExpression(), scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             cron表达式
     */
    public void updateJobCron(String triggerName, String triggerGroupName, String cron) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return;
        }
        String oldTime = trigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron)) {
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名，触发器组名
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            trigger = (CronTrigger) triggerBuilder.build();
            // 修改任务的触发时间
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 暂停所有任务
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组名
     */
    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组名
     */
    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     */
    public void deleteJob(String jobName, String jobGroupName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroupName);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.deleteJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     */
    public void deleteJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 删除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除任务组
     *
     * @param jobGroupName 任务组名
     */
    public void deleteJobGroup(String jobGroupName) throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroupName);
        Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
        List<JobKey> jobKeyList = new ArrayList<>(jobKeySet);
        scheduler.deleteJobs(jobKeyList);
    }

    /**
     * 获取任务是否存在
     * <p>
     * STATE_BLOCKED 4 阻塞
     * STATE_COMPLETE 2 完成
     * STATE_ERROR 3 错误
     * STATE_NONE -1 不存在
     * STATE_NORMAL 0 正常
     * STATE_PAUSED 1 暂停
     */
    public Boolean notExists(String triggerName, String triggerGroupName) throws SchedulerException {
        return scheduler.getTriggerState(TriggerKey.triggerKey(triggerName, triggerGroupName)) == Trigger.TriggerState.NONE;
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param cron             时间设置，参考quartz说明文档
     */
    @SuppressWarnings({"unchecked"})
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String jobClass, String cron, Map<String, Object> params) {
        try {
            Class<? extends Job> aClass = (Class<? extends Job>) Class.forName(jobClass).newInstance().getClass();
            // 任务名，任务组，任务执行类
            JobDetail job = JobBuilder.newJob(aClass).withIdentity(jobName, jobGroupName).build();
            // 任务参数
            job.getJobDataMap().putAll(params);
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名，触发器组名
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(job, trigger);
            // 启动
            if (!scheduler.isShutdown()) scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName     任务名
     * @param triggerName 触发器名
     * @param jobClass    任务
     * @param cron        时间设置，参考quartz说明文档
     */
    @SuppressWarnings({"unchecked"})
    public void addJob(String jobName, String triggerName, String jobClass, String cron, Map<String, Object> params) {
        try {
            Class<? extends Job> aClass = (Class<? extends Job>) Class.forName(jobClass).newInstance().getClass();
            // 任务名，任务执行类
            JobDetail job = JobBuilder.newJob(aClass).withIdentity(jobName).build();
            // 任务参数
            job.getJobDataMap().putAll(params);
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名
            triggerBuilder.withIdentity(triggerName);
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(job, trigger);
            // 启动
            if (!scheduler.isShutdown()) scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 以下方法把参数封装成了对象，方便传参

    /**
     * 添加一个定时任务
     *
     * @param task 任务
     */
    @SuppressWarnings({"unchecked"})
    public void addJob(CronTask task) {
        try {
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            Class<? extends Job> jobClass = (Class<? extends Job>) (Class.forName(task.getBeanClass()).newInstance().getClass());
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(task.getJobName(), task.getJobGroupName())
                    //参数
                    .usingJobData("parameter", task.getParameter())
                    .build();
            // 判断map参数是否有值
            if (!CollectionUtils.isEmpty(task.getParameterMap())) {
                Set<String> set = task.getParameterMap().keySet();
                if (!CollectionUtils.isEmpty(set)) {
                    for (String key : set) {
                        jobDetail.getJobDataMap().put(key, task.getParameterMap().get(key));
                    }
                }
            }
            // 定义调度触发规则、使用cornTrigger规则、触发器key
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getJobName(), task.getJobGroupName()).startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression())).startNow().build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有计划中的任务列表
     */
    public List<CronTask> getAllJob() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<CronTask> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            Class<? extends Job> jobClass = scheduler.getJobDetail(jobKey).getJobClass();
            for (Trigger trigger : triggers) {
                CronTask job = new CronTask();
                job.setJobName(jobKey.getName());
                job.setJobGroupName(jobKey.getGroup());
                job.setBeanClass(jobClass.getName());
                job.setDescription("触发器：" + trigger.getKey().getName());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 所有正在运行的job（获取的不是打开状态的任务，而是正在执行的任务）
     */
    public List<CronTask> getRunningJob() throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<CronTask> jobList = new ArrayList<>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            CronTask job = new CronTask();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroupName(jobKey.getGroup());
            job.setBeanClass(jobDetail.getJobClass().getName());
            job.setDescription("触发器：" + trigger.getKey().getName());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param task 任务
     */
    public void pauseJob(CronTask task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroupName());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param task 任务
     */
    public void resumeJob(CronTask task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroupName());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param task 任务
     */
    public void deleteJob(CronTask task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroupName());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即执行job
     *
     * @param task 任务
     */
    public void runJobNow(CronTask task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroupName());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param task 任务
     */
    public void updateJobCron(CronTask task) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(task.getJobName(), task.getJobGroupName());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }
}