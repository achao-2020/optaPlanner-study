package com.achao.cloud;

import com.achao.cloud.solution.CloudBalance;
import com.achao.cloud.solution.CloudBalancingGenerator;
import com.achao.cloud.task.CloudProcess;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

/**
 * 通过xml配置
 * @author licc3
 * @date 2023-6-1 10:35
 */
public class HelloWordWithXML {

    public static void main(String[] args) {
        // 加载规划模型配置
        SolverFactory<CloudBalance> solverFactory = SolverFactory.createFromXmlResource("com/achao/cloud/cloudBalancingSolverConfig.xml");
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