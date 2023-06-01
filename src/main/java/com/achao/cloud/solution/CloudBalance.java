package com.achao.cloud.solution;

import com.achao.cloud.resource.CloudComputer;
import com.achao.cloud.task.CloudProcess;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

/**
 * @author licc3
 * @date 2023-6-1 10:33
 */
@NoArgsConstructor
@Data
@PlanningSolution
public class CloudBalance {
    @PlanningId
    private long id;

    /**
     * 问题事实（资源）
     */
    @ValueRangeProvider(id = "computerRange")
    @ProblemFactCollectionProperty
    private List<CloudComputer> computerList;

    /**
     * 计划实体（任务）
     */
    @PlanningEntityCollectionProperty
    private List<CloudProcess> processList;

    /**
     * 硬/软两个级别约束分数
     */
    @PlanningScore
    private HardSoftScore score;

    public CloudBalance(long id, List<CloudComputer> computerList, List<CloudProcess> processList) {
        this.id = id;
        this.computerList = computerList;
        this.processList = processList;
    }
}