package com.achao.cloud.task;

import com.achao.cloud.resource.CloudComputer;
import com.achao.cloud.solution.CloudBalance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * @author licc3
 * @date 2023-6-1 10:25
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@PlanningEntity
public class CloudProcess {
    @PlanningId
    private long id;
    private int requiredCpuPower;
    private int requiredMemory;
    private int requiredNetworkBandwidth;

    /**
     * 指定插槽
     * 关联被@PlanningSolution修饰类的@ValueRangeProvider(id = "computerRange") 修饰属性id
     * {@link CloudBalance#computerList}
     */
    @PlanningVariable(valueRangeProviderRefs = {"computerRange"})
    private CloudComputer computer;

}