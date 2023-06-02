package com.achao.cloud;

import com.achao.cloud.solution.CloudBalance;
import com.achao.cloud.solution.CloudBalancingGenerator;
import com.achao.cloud.task.CloudProcess;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;

/**
 * 通过配置类启动
 *
 * @author licc3
 * @date 2023-6-2 15:34
 */
public class HelloWordWithClass {
    public static void main(String[] args) {
        // 加载规划模型配置
        SolverFactory<CloudBalance> solverFactory = SolverFactory.create(new SolverConfig().withSolutionClass(CloudBalance.class)
                .withEntityClasses(CloudProcess.class).withEasyScoreCalculatorClass(CloudBalancingEasyScoreCalculator.class)
                .withTerminationConfig(new TerminationConfig().withBestScoreFeasible(true)));
        // 生成解决类
        Solver<CloudBalance> solver = solverFactory.buildSolver();
        // 准备2台计算机，4个进程
        CloudBalance cloudBalance = new CloudBalancingGenerator().createCloudBalance();
        // 解决问题，生成解
        CloudBalance solution = solver.solve(cloudBalance);

        deplaySolution(solution);
    }

    private static void deplaySolution(CloudBalance solution) {
        for (CloudProcess cloudProcess : solution.getProcessList()) {
            System.out.println("process【" + cloudProcess + "】分配到了computer【" + cloudProcess.getComputer() + "】中执行");
        }
    }
}