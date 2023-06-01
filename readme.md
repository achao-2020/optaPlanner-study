# optaPlanner hello-word

    这个入门教程是参考官方文档的[Cloud Balancing Demonstration](https://docs.optaplanner.org/7.8.0.Final/optaplanner-docs/html_single/index.html?spm=a2c6h.12873639.article-detail.7.2e763a9enqyqj9#quickStart)，便于后面学习。

**规划问题背景：**

在不同的cpu和ram资源下，运行多个进程。如下图

![](E:\sourceCode\optaplanner\examples\helloword\png\cloudBalanceUseCase.png)

在这个计划的任务数量（4个进程），需要分配到两个有限的computer中运行，计算最优解，也就是图中Optimal solution的结果。





## maven依赖

```xml
        <dependency>
            <groupId>org.optaplanner</groupId>
            <artifactId>optaplanner-core</artifactId>
            <version>9.38.0.Final</version>
        </dependency>
```

## 代码设计

类图关系

![](E:\sourceCode\optaplanner\examples\helloword\png\cloudBalanceClassDiagram.png)

### 资源和任务

**computer定义**

computer做为有限的资源,也可以叫做插槽，需要执行planningId，并在task中指定，里面的属性可以作为约束判断。

```java
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
     * 交换区大小(demo未使用约束)
     */
    private int networkBandwidth;
    /**
     * 进程数量
     */
    private int cost;
}
```

**process定义**

process作为需要分配的任务，分配到computer（插槽）中执行，属性为需要占用computer的资源。

```java
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
```

### 规划解决方案

**solution定义**

规划解决方案对象。关联问题事实和计划实体。

```java
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
```

**规划配置入口**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<solver>
    <!-- Domain model configuration -->
    <scanAnnotatedClasses/>
    <!--解决方案对象-->
    <solutionClass>com.achao.cloud.solution.CloudBalance</solutionClass>
    <!--计划实体-->
    <entityClass>com.achao.cloud.task.CloudProcess</entityClass>
    <!-- Score configuration -->
    <scoreDirectorFactory>
        <!--计算分数的两种方式，简单类和droll规则引擎-->
        <easyScoreCalculatorClass>com.achao.cloud.CloudBalancingEasyScoreCalculator</easyScoreCalculatorClass>
        <!--<scoreDrl>org/optaplanner/examples/cloudbalancing/solver/cloudBalancingScoreRules.drl</scoreDrl>-->
    </scoreDirectorFactory>

    <!-- Optimization algorithms configuration -->
    <termination>
        <!--最少运行时间，即便已经求得最优解，也会运行到这个时间才结束-->
        <secondsSpentLimit>1</secondsSpentLimit>
        <!--如果配置的是这个，则求得最优解后返回，这个demo的排列组合为8次，会尝试所有的排列组合-->
        <!--        <bestScoreFeasible>true</bestScoreFeasible>-->
    </termination>
</solver>
```




**启动**

```java
public class CloudComputerHelloWord {

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
```

```json
process【CloudProcess(id=1, requiredCpuPower=5, requiredMemory=5, requiredNetworkBandwidth=1, computer=CloudComputer(id=1, cpuPower=7, memory=6, networkBandwidth=10, cost=2))】分配到了computer【CloudComputer(id=1, cpuPower=7, memory=6, networkBandwidth=10, cost=2)】中执行
process【CloudProcess(id=2, requiredCpuPower=4, requiredMemory=3, requiredNetworkBandwidth=2, computer=CloudComputer(id=2, cpuPower=6, memory=6, networkBandwidth=10, cost=2))】分配到了computer【CloudComputer(id=2, cpuPower=6, memory=6, networkBandwidth=10, cost=2)】中执行
process【CloudProcess(id=3, requiredCpuPower=2, requiredMemory=3, requiredNetworkBandwidth=0, computer=CloudComputer(id=2, cpuPower=6, memory=6, networkBandwidth=10, cost=2))】分配到了computer【CloudComputer(id=2, cpuPower=6, memory=6, networkBandwidth=10, cost=2)】中执行
process【CloudProcess(id=5, requiredCpuPower=2, requiredMemory=1, requiredNetworkBandwidth=0, computer=CloudComputer(id=1, cpuPower=7, memory=6, networkBandwidth=10, cost=2))】分配到了computer【CloudComputer(id=1, cpuPower=7, memory=6, networkBandwidth=10, cost=2)】中执行
```

从打印的内容来看，每个computer都占满了核心数和内存数，达到了最优的分配。
