package com.fever.events;

import com.fever.events.verticles.MainVerticle;
import com.fever.events.verticles.MongoVerticle;
import com.fever.resthandler.runner.Runner;
import io.netty.channel.DefaultChannelId;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final long MAX_EXECUTION_TIME_SEC = 45;
    private static final int THREADS_PER_PROCESSOR = 4;

    public static void main(String[] args) {
        // Hack in order to avoid a noisy blocked thread exception at initialization time. Only happens once.
        DefaultChannelId.newInstance();
        run(new DeploymentOptions(), false);
    }

    @SuppressWarnings("CheckReturnValue")
    public static void startBlocking(DeploymentOptions deploymentOptions) {
        deploymentOptions.setInstances(1);
        run(deploymentOptions, true);
    }

    private static void run(DeploymentOptions deploymentOptions, boolean blocking) {
        // Deployment options to set the verticle as a worker
        final var instancesSize = Optional.ofNullable(System.getenv("WORKER_THREAD_POOL_SIZE"))
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .orElseGet(() -> Runtime.getRuntime().availableProcessors() * THREADS_PER_PROCESSOR);

        final List<Class<? extends AbstractVerticle>> verticleList = Arrays.asList(
                MongoVerticle.class,
                MainVerticle.class);

        var deploymentOptionsList = Arrays.asList(
                createWorkerOptions(instancesSize),
                deploymentOptions);

        // Init Microservice
        if (blocking) {
            Runner.blockingRun(verticleList, deploymentOptionsList);
        } else {
            Runner.run(verticleList, deploymentOptionsList);
        }
    }

    private static DeploymentOptions createWorkerOptions(final int instancesSize) {
        return new DeploymentOptions()
                .setWorker(true)
                .setInstances(instancesSize) // matches the worker pool size below
                .setWorkerPoolName("asycEvents-worker-pool")
                .setWorkerPoolSize(100)
                .setMaxWorkerExecuteTime(MAX_EXECUTION_TIME_SEC)
                .setMaxWorkerExecuteTimeUnit(TimeUnit.SECONDS);
    }
}