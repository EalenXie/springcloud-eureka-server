package name.ealen.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by EalenXie on 2018/9/21 14:20.
 * 要记录的Eureka Client信息实例(这里示例,按照自己需要自定义)
 */
@Table
@Entity
public class EurekaClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String uuid;
    private String applicationName;                     //Eureka Client的appName
    private String instanceId;                          //Eureka Client的instanceId
    private String hostname;                            //Eureka Client的hostname
    private String status;                              //Eureka Client在Eureka Server上面的状态
    private String homePageUrl;                         //Eureka Client的首页Url
    private String statusPageUrl;                       //Eureka Client的状态页Url
    private String healthCheckUrl;                      //Eureka Client的健康检查页Url
    private String eurekaInstanceId;                    //Eureka Client注册的Eureka Server的InstanceId
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;                          //Eureka Client注册到Eureka Server的时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLeaveTime;                         //Eureka Client上次的下线时间
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public String getStatusPageUrl() {
        return statusPageUrl;
    }

    public void setStatusPageUrl(String statusPageUrl) {
        this.statusPageUrl = statusPageUrl;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getEurekaInstanceId() {
        return eurekaInstanceId;
    }

    public void setEurekaInstanceId(String eurekaInstanceId) {
        this.eurekaInstanceId = eurekaInstanceId;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLeaveTime() {
        return lastLeaveTime;
    }

    public void setLastLeaveTime(Date lastLeaveTime) {
        this.lastLeaveTime = lastLeaveTime;
    }
}
