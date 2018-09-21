package name.ealen.function;

import com.netflix.appinfo.InstanceInfo;
import name.ealen.dao.EurekaClientRepository;
import name.ealen.dao.EurekaRepository;
import name.ealen.model.EurekaClientEntity;
import name.ealen.model.EurekaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * Created by EalenXie on 2018/9/20 15:37.
 */
@Component
public class EurekaEventHandler {
    private static final Logger log = LoggerFactory.getLogger(EurekaEventHandler.class);

    @Resource
    private EurekaRepository eurekaRepository;

    @Resource
    private EurekaClientRepository eurekaClientRepository;

    @Resource
    private EurekaInstanceConfigBean eurekaInstance;

    @Resource
    private EurekaClientConfigBean eurekaClientConfigBean;

    /**
     * 获取Application Profiles
     */
    private String getApplicationProfile(StandardEnvironment environment) {
        StringBuilder profile = new StringBuilder(environment.getDefaultProfiles()[0]);
        if (environment.getActiveProfiles().length != 0) {
            profile = new StringBuilder(environment.getActiveProfiles()[0]);
            if (environment.getActiveProfiles().length > 1) {
                for (int i = 1; i < environment.getActiveProfiles().length; i++) {
                    profile.append(",").append(environment.getActiveProfiles()[i]);
                }
            }
        }
        return profile.toString();
    }


    /**
     * Eureka启动,记录Eureka启动的信息(自定义EurekaEntity)
     *
     * @param eureka EurekaServer服务实例信息
     */
    @Async
    public void recordEurekaStartUp(EurekaServerConfigBean eureka) {
        try {
            StandardEnvironment environment = (StandardEnvironment) eureka.getPropertyResolver();
            String profile = getApplicationProfile(environment);
            MapPropertySource clientHostInfo = (MapPropertySource) environment.getPropertySources().get("springCloudClientHostInfo");
            if (clientHostInfo != null) {
                EurekaEntity eurekaEntity;
                String hostname = eurekaInstance.getHostname();
                log.info("Eureka Server Start by hostname : {}", hostname);
                String applicationName = eurekaInstance.getAppname();
                String instanceId = eurekaInstance.getInstanceId();
                log.info("Eureka Server Start with InstanceId : {}", instanceId);
                String defaultZone = eurekaClientConfigBean.getServiceUrl().get("defaultZone");
                log.info("Eureka Server defaultZone : {}", defaultZone);
                boolean isEnableSelfPreservation = eureka.isEnableSelfPreservation();
                log.info("Eureka Server enable-self-preservation : {}", isEnableSelfPreservation);
                Integer leaseRenewInterval = eurekaInstance.getLeaseRenewalIntervalInSeconds();
                log.info("Eureka Server lease-renewal-interval-in-seconds : {}s", leaseRenewInterval);
                Integer leaseExpirationDuration = eurekaInstance.getLeaseExpirationDurationInSeconds();
                log.info("Eureka Server lease-expiration-duration-in-seconds : {}s", leaseExpirationDuration);
                Integer registryFetchInterval = eurekaClientConfigBean.getRegistryFetchIntervalSeconds();
                log.info("Eureka Server registry-fetch-interval-seconds : {}s", registryFetchInterval);
                Integer replicationInterval = eurekaClientConfigBean.getInstanceInfoReplicationIntervalSeconds();
                log.info("Eureka Server instance-info-replication-interval-seconds : {}s", replicationInterval);
                if (applicationName != null) {
                    eurekaEntity = eurekaRepository.findByInstanceId(instanceId);
                    if (eurekaEntity == null) {
                        eurekaEntity = new EurekaEntity();
                        eurekaEntity.setApplicationName(applicationName);
                        eurekaEntity.setHostname(hostname);
                        eurekaEntity.setInstanceId(instanceId);
                        eurekaEntity.setUuid(UUID.randomUUID().toString().replace("-", ""));
                    }
                    eurekaEntity.setStartTime(new Date());
                    eurekaEntity.setProfile(profile);
                    eurekaEntity.setEnableSelfPreservation(isEnableSelfPreservation);
                    eurekaEntity.setDefaultZone(defaultZone);
                    eurekaEntity.setRegistryFetchInterval(registryFetchInterval);
                    eurekaEntity.setInstanceReplicationInterval(replicationInterval);
                    eurekaEntity.setLeaseRenewalInterval(leaseRenewInterval);
                    eurekaEntity.setLeaseExpirationDuration(leaseExpirationDuration);
                    eurekaEntity.setStatus(eurekaInstance.getInitialStatus().toString());
                    eurekaRepository.save(eurekaEntity);
                    log.info("Started Eureka Server Record Success ");
                }
            }
        } catch (Exception e) {
            log.warn("Started Eureka Server Record failure : {}", e.getMessage());
        }
    }


    /**
     * 服务注册,记录注册的服务实例信息
     *
     * @param instanceInfo 要注册的服务实例信息
     */
    @Async
    public void recordInstanceRegister(InstanceInfo instanceInfo) {
        try {
            log.info("Instance Register , name : {}", instanceInfo.getAppName());
            log.info("Instance Register , id : {}", instanceInfo.getId());
            log.info("Instance Register , ipAddress : {}", instanceInfo.getIPAddr());
            log.info("Instance Register , status : {} ", instanceInfo.getStatus());
            //Eureka服务器在接收到实例的最后一次发出的心跳后，需要等待多久才可以将此实例删除，默认为90秒
            log.info("Instance Register , durationInSecs : {}s", instanceInfo.getLeaseInfo().getDurationInSecs());
            EurekaClientEntity eurekaClientEntity = eurekaClientRepository.findByInstanceId(instanceInfo.getInstanceId());
            if (eurekaClientEntity == null) {
                eurekaClientEntity = new EurekaClientEntity();
                eurekaClientEntity.setApplicationName(instanceInfo.getAppName());
                eurekaClientEntity.setInstanceId(instanceInfo.getInstanceId());
                eurekaClientEntity.setHostname(instanceInfo.getHostName());
                eurekaClientEntity.setUuid(UUID.randomUUID().toString().replace("-", ""));
            }
            eurekaClientEntity.setRegisterTime(new Date());
            eurekaClientEntity.setHomePageUrl(instanceInfo.getHomePageUrl());
            eurekaClientEntity.setHealthCheckUrl(instanceInfo.getHealthCheckUrl());
            eurekaClientEntity.setStatusPageUrl(instanceInfo.getStatusPageUrl());
            eurekaClientEntity.setEurekaInstanceId(eurekaInstance.getInstanceId());
            eurekaClientEntity.setStatus(instanceInfo.getStatus().toString());
            eurekaClientRepository.save(eurekaClientEntity);
            log.info("Instance Register {} Record Success ", instanceInfo.getInstanceId());
        } catch (Exception e) {
            log.info("Instance Register {} Record failure : {}", instanceInfo.getInstanceId(), e.getMessage());
        }
        System.out.println();
    }


    /**
     * 服务下线,记录下线的服务实例信息
     *
     * @param instanceId 要注册的服务实例信息的instanceId
     */
    @Async
    public void recordInstanceCancel(String instanceId) {
        try {
            log.info("Instance Cancel ");
            EurekaClientEntity eurekaClientEntity = eurekaClientRepository.findByInstanceId(instanceId);
            eurekaClientEntity.setStatus(InstanceInfo.InstanceStatus.DOWN.toString());
            eurekaClientRepository.save(eurekaClientEntity);
            log.info("Instance Cancel {} Record Success ", instanceId);
        } catch (Exception e) {
            log.info("Instance Cancel {} Record failure : {}", instanceId, e.getMessage());
        }
    }

}
