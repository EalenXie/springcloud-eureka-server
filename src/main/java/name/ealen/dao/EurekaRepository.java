package name.ealen.dao;

import name.ealen.model.EurekaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by EalenXie on 2018/9/20 15:04.
 */
public interface EurekaRepository extends JpaRepository<EurekaEntity, Integer> {

    EurekaEntity findByInstanceId(String instanceId);

}
