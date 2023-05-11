package com.fever.resthandler.runner;

import com.fever.resthandler.config.SharedConfig;
import com.fever.utils.EnvUtils;
import io.reactivex.Single;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.micrometer.Label;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.Vertx;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Runner is responsible of the verticles deployment and the configuration loading. The configuration is loaded from a
 * YAML file located in ${VERTX_CONFIG_PATH}.
 */
public class Runner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class.getName());

    private static final MicrometerMetricsOptions DEFAULT_METRICS_OPTIONS = (new MicrometerMetricsOptions()
            .setPrometheusOptions(Prometheus.createVertxMetricsOptions()))
            .setJvmMetricsEnabled(true)
            .setLabels(EnumSet.of(Label.HTTP_METHOD, Label.HTTP_CODE, Label.HTTP_PATH, Label.POOL_TYPE))
            .setEnabled(true);

    private Runner() {
        // do nothing
    }

    public static void run(final List<Class<? extends AbstractVerticle>> classes,
                           final List<DeploymentOptions> deploymentOptions) {
        run(classes, new VertxOptions(), deploymentOptions, DEFAULT_METRICS_OPTIONS);
    }

    public static void run(final List<Class<? extends AbstractVerticle>> verticles,
                           final VertxOptions options,
                           final List<DeploymentOptions> deploymentOptions,
                           final MicrometerMetricsOptions metricsOpt) {
        final List<String> verticleNames = verticles.stream().map(Class::getName).collect(Collectors.toList());
        final Consumer<Vertx> runner = vertx -> getConfig(vertx).subscribe(config -> {
            SharedConfig.getSuperInstance().init(config);

            for (int i = 0; i < verticleNames.size(); i++) {
                if (deploymentOptions.get(i) != null) {
                    vertx.deployVerticle(verticleNames.get(i), deploymentOptions.get(i));
                } else {
                    vertx.deployVerticle(verticleNames.get(i));
                }
            }

        }, throwable -> {
            LOGGER.error("Server failed to load configuration", throwable);
            vertx.close();
        });

        deployVertx(options, runner, metricsOpt);
    }

    private static void deployVertx(final VertxOptions options,
                                    final Consumer<Vertx> runner,
                                    final MicrometerMetricsOptions metricsOpt) {
        runner.accept(Vertx.vertx(options.setMetricsOptions(metricsOpt)));
    }

    /**
     * Vertx blocking run. Used for cukemain acceptance tests.
     */
    public static void blockingRun(final List<Class<? extends AbstractVerticle>> classes,
                                   final List<DeploymentOptions> deploymentOptions) {
        final List<String> verticleNames = classes.stream().map(Class::getName).collect(Collectors.toList());
        deployBlocking(Vertx.vertx(new VertxOptions()), verticleNames, deploymentOptions);
    }

    private static void deployBlocking(final Vertx vertx,
                                       final List<String> verticleNames,
                                       final List<DeploymentOptions> deploymentOptions) {
        final JsonObject configFile = getConfig(vertx).blockingGet();
        final JsonObject config = mergeConfigs(configFile, deploymentOptions);

        SharedConfig.getSuperInstance().init(config);
        for (int i = 0; i < verticleNames.size(); i++) {
            if (deploymentOptions.get(i) != null) {
                vertx.deployVerticle(verticleNames.get(i), deploymentOptions.get(i));
            } else {
                vertx.deployVerticle(verticleNames.get(i));
            }
        }
    }

    /**
     * Merges config file defined in VERTX_CONFIG_PATH with the deployment options. DeploymentOptions has priority over
     * fileConfig
     */
    private static JsonObject mergeConfigs(final JsonObject fileConfig,
                                           final List<DeploymentOptions> deploymentOptions) {
        final Optional<DeploymentOptions> first = deploymentOptions.stream()
                .filter(deployOptions -> deployOptions.getConfig() != null)
                .findFirst();

        if (first.isPresent()) {
            final JsonObject overridenConfig = first.get().getConfig();
            fileConfig.mergeIn(overridenConfig, 2);
        }
        return fileConfig;
    }

    public static Single<JsonObject> getConfig(final Vertx vertx) {
        final var configPath = EnvUtils.findEnv("VERTX_CONFIG_PATH");
        if ((configPath == null) || (configPath.isEmpty())) {
            LOGGER.warn("Missing VERTX_CONFIG_PATH environment variable.");
        }
        return ConfigRetriever.create(vertx).rxGetConfig();
    }
}
