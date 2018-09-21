package name.ealen.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by EalenXie on 2018/9/20 14:46.
 * 要记录的Eureka Server信息实例(这里示例,按照自己需要自定义)
 */
@Table
@Entity
public class EurekaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String uuid;                                //Eureka Server的唯一标识
    private String applicationName;                     //Eureka Server的应用名
    private String profile;                             //Eureka Server的应用启动Profile
    private String defaultZone;                         //Eureka Server指定服务注册中心的位置
    private boolean enableSelfPreservation;             //Eureka Server是否开启自我保护模式
    private String hostname;                            //Eureka Server的hostname
    private String instanceId;                          //Eureka Server的InstanceId
    private Integer leaseRenewalInterval;               //Eureka Client向Eureka Server发送心跳周期(s)
    private Integer leaseExpirationDuration;            //Eureka Server接收实例的最后一次发出的心跳后,删除需要等待时间(s)
    private String status;                              //Eureka Server的当前状态
    private Integer registryFetchInterval;              //Eureka Server拉取服务注册信息间隔时间(s)
    private Integer instanceReplicationInterval;        //Eureka Server复制实例变化信息到Eureka服务器所需要的时间间隔(s)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;                             //Eureka Server的本次启动时间点

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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDefaultZone() {
        return defaultZone;
    }

    public void setDefaultZone(String defaultZone) {
        this.defaultZone = defaultZone;
    }

    public boolean isEnableSelfPreservation() {
        return enableSelfPreservation;
    }

    public void setEnableSelfPreservation(boolean enableSelfPreservation) {
        this.enableSelfPreservation = enableSelfPreservation;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getLeaseRenewalInterval() {
        return leaseRenewalInterval;
    }

    public void setLeaseRenewalInterval(Integer leaseRenewalInterval) {
        this.leaseRenewalInterval = leaseRenewalInterval;
    }

    public Integer getLeaseExpirationDuration() {
        return leaseExpirationDuration;
    }

    public void setLeaseExpirationDuration(Integer leaseExpirationDuration) {
        this.leaseExpirationDuration = leaseExpirationDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRegistryFetchInterval() {
        return registryFetchInterval;
    }

    public void setRegistryFetchInterval(Integer registryFetchInterval) {
        this.registryFetchInterval = registryFetchInterval;
    }

    public Integer getInstanceReplicationInterval() {
        return instanceReplicationInterval;
    }

    public void setInstanceReplicationInterval(Integer instanceReplicationInterval) {
        this.instanceReplicationInterval = instanceReplicationInterval;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
