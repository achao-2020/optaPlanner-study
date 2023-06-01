package com.achao.cloud.resource;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.lookup.PlanningId;

/**
 * @author licc3
 * @date 2023-6-1 10:14
 */
@NoArgsConstructor
@Data
public class CloudComputer {
    @PlanningId
    private long id;
    /**
     * 核心数
     */
    private int cpuPower;
    /**
     * 缓存大小
     */
    private int memory;
    /**
     * 交换区大小
     */
    private int networkBandwidth;
    /**
     * 进程数量
     */
    private int cost;
}