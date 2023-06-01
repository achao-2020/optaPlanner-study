package com.achao.cloud.solution;

import com.achao.cloud.resource.CloudComputer;
import com.achao.cloud.task.CloudProcess;
import org.optaplanner.core.impl.util.CollectionUtils;

import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * @author licc3
 * @date 2023-6-1 10:37
 */
public class CloudBalancingGenerator {

    private Random random;

    public CloudBalance createCloudBalance() {
        CloudComputer cloudComputer1 = new CloudComputer();
        cloudComputer1.setId(1L);
        cloudComputer1.setCpuPower(7);
        cloudComputer1.setMemory(6);
        cloudComputer1.setNetworkBandwidth(10);
        cloudComputer1.setCost(0);

        CloudComputer cloudComputer2 = new CloudComputer();
        cloudComputer2.setId(2L);
        cloudComputer2.setCpuPower(6);
        cloudComputer2.setMemory(6);
        cloudComputer2.setNetworkBandwidth(10);
        cloudComputer2.setCost(0);

        CloudBalance cloudBalance = new CloudBalance();
        cloudBalance.setComputerList(new ArrayList<>(Arrays.asList(cloudComputer1, cloudComputer2)));

        CloudProcess cloudProcess1 = new CloudProcess();
        cloudProcess1.setId(1L);
        cloudProcess1.setRequiredCpuPower(5);
        cloudProcess1.setRequiredMemory(5);
        cloudProcess1.setRequiredNetworkBandwidth(1);
        CloudProcess cloudProcess2 = new CloudProcess();
        cloudProcess2.setId(2L);
        cloudProcess2.setRequiredCpuPower(4);
        cloudProcess2.setRequiredMemory(3);
        cloudProcess2.setRequiredNetworkBandwidth(2);
        CloudProcess cloudProcess3 = new CloudProcess();
        cloudProcess3.setId(3L);
        cloudProcess3.setRequiredCpuPower(2);
        cloudProcess3.setRequiredMemory(3);
        cloudProcess3.setRequiredNetworkBandwidth(0);
        CloudProcess cloudProcess4 = new CloudProcess();
        cloudProcess4.setId(5L);
        cloudProcess4.setRequiredCpuPower(2);
        cloudProcess4.setRequiredMemory(1);
        cloudProcess4.setRequiredNetworkBandwidth(0);

        cloudBalance.setProcessList(new ArrayList<>(Arrays.asList(cloudProcess1, cloudProcess2, cloudProcess3, cloudProcess4)));

        return cloudBalance;
    }
}