package com.achao.cloud;

import com.achao.cloud.resource.CloudComputer;
import com.achao.cloud.solution.CloudBalance;
import com.achao.cloud.task.CloudProcess;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

/**
 * 计算分数的简单类
 * @author licc3
 * @date 2023-6-1 11:20
 */
public class CloudBalancingEasyScoreCalculator implements EasyScoreCalculator<CloudBalance, HardSoftScore> {
    private int count = 0;

    /**
     * 这个方法，optaPlanner自动将@PlanningEntityCollectionProperty和@ProblemFactCollectionProperty进行排列组合
     * 每重新排列组合之后，这个方法用来计算硬约束、软约束的分数，帮助optaPlanner来获得最优解
     * @param cloudBalance
     * @return
     */
    @Override
    public HardSoftScore calculateScore(CloudBalance cloudBalance) {
        // 硬约束
        int hardScore = 0;
        // 软约束
        int softScore = 0;
        for (CloudComputer computer : cloudBalance.getComputerList()) {
            int cpuPowerUsage = 0;
            int memoryUsage = 0;
            int networkBandwidthUsage = 0;
            boolean used = false;
            computer.setCost(0);
            // Calculate usage
            for (CloudProcess process : cloudBalance.getProcessList()) {
                // 当前computer分配给了当前process
                if (computer.equals(process.getComputer())) {
                    // 计算当前computer的资源被使用量
                    cpuPowerUsage += process.getRequiredCpuPower();
                    memoryUsage += process.getRequiredMemory();
                    networkBandwidthUsage += process.getRequiredNetworkBandwidth();
                    // 进程数+1
                    computer.setCost(computer.getCost() + 1);
                    used = true;
                }
            }

            // Hard constraints
            int cpuPowerAvailable = computer.getCpuPower() - cpuPowerUsage;
            if (cpuPowerAvailable < 0) {
                // 硬约束减分
                hardScore += cpuPowerAvailable;
            }
            int memoryAvailable = computer.getMemory() - memoryUsage;
            if (memoryAvailable < 0) {
                // 硬约束减分
                hardScore += memoryAvailable;
            }
            int networkBandwidthAvailable = computer.getNetworkBandwidth() - networkBandwidthUsage;
            if (networkBandwidthAvailable < 0) {
                // 硬约束减分
                hardScore += networkBandwidthAvailable;
            }

            // Soft constraints
            if (used) {
                // 进程数量越多，软约束分数越高
                softScore += computer.getCost();
            }
        }
        System.out.println("第【" + this.count++ + "】次排列的分数计算：hardScore:" + hardScore + ", softScore:" + softScore);
        return HardSoftScore.of(hardScore, softScore);
    }
}