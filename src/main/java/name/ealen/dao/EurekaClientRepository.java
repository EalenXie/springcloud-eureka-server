package name.ealen.dao;

import name.ealen.model.EurekaClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by EalenXie on 2018/9/20 15:04.
 */
public interface EurekaClientRepository extends JpaRepository<EurekaClientEntity, Integer> {

    EurekaClientEntity findByInstanceId(String instanceId);

}
