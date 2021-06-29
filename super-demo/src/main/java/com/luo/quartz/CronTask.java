package com.luo.quartz;

import java.util.Map;

public class CronTask {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 任务分组名
     */
    private String jobGroupName;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String beanClass;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务状态
     */
    private String jobStatus;

    /**
     * 参数的map
     */
    //@Transient
    private Map<String, String> parameterMap;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, String> parameterMap) {
        this.parameterMap = parameterMap;
    }
}