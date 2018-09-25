package name.ealen.listener;

import name.ealen.function.EurekaEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by EalenXie on 2018/9/20 14:46.
 * EurekaServerEventListener 监听Eureka的事件行为
 * 注 : EurekaInstanceRegisteredEvent,EurekaInstanceCanceledEvent,EurekaInstanceRenewedEvent
 * 这三个事件会可能触发多次,条件是不相同的,一次event.replication=false,一次或多次为true
 * replication属性的意思为是否复制,主注册地址为false,其它的为true
 */
@Component
public class EurekaServerEventListener {

    private static final Logger log = LoggerFactory.getLogger(EurekaServerEventListener.class);

    @Resource
    private EurekaEventHandler eurekaEventHandler;


    /**
     * Eureka Server 注册事件
     */
    @EventListener
    public void eurekaRegister(EurekaRegistryAvailableEvent event) {
        log.info("Eureka Server Register at timestamp : {}", event.getTimestamp());
        //write your logic..........
    }

    /**
     * Eureka Server 启动事件
     */
    @EventListener
    public void serverStart(EurekaServerStartedEvent event) {
        Object source = event.getSource();
        if (source instanceof EurekaServerConfigBean) {
            EurekaServerConfigBean eureka = (EurekaServerConfigBean) source;
            eurekaEventHandler.recordEurekaStartUp(eureka);
        }
        //write your logic..........
    }

    /**
     * 服务注册事件
     */
    @EventListener(condition = "#event.replication==false")
    public void instanceRegister(EurekaInstanceRegisteredEvent event) {
        eurekaEventHandler.recordInstanceRegister(event.getInstanceInfo());
        //write your logic..........
    }

    /**
     * 服务下线事件
     */
    @EventListener(condition = "#event.replication==false")
    public void instanceCancel(EurekaInstanceCanceledEvent event) {
        eurekaEventHandler.recordInstanceCancel(event.getServerId());
        //write your logic..........
    }

    /**
     * 服务续约事件
     */
    @EventListener(condition = "#event.replication==false")
    public void instanceRenewed(EurekaInstanceRenewedEvent event) {
        //.....
    }


}
