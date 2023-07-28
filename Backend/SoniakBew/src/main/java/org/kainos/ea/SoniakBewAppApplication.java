package org.kainos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.kainos.ea.resources.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoniakBewAppApplication extends Application<SoniakBewAppConfiguration> {


    public static void main(final String[] args) throws Exception {
        new SoniakBewAppApplication().run(args);
    }

    @Override
    public String getName() {
        return "SoniakBewApp";
    }

    @Override
    public void initialize(final Bootstrap<SoniakBewAppConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<SoniakBewAppConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(SoniakBewAppConfiguration soniakBewAppConfiguration) {
                return soniakBewAppConfiguration.getSwagger();
            }
        });
    }

    @Override
    public void run(final SoniakBewAppConfiguration configuration,
                    final Environment environment) {

        // TODO: implement application
        environment.jersey().register(new EmployeeController());
        environment.jersey().register(new ClientController());
        environment.jersey().register(new EmployeeController());
        environment.jersey().register(new ProjectController());
        environment.jersey().register(new AuthController());
        environment.jersey().register(new EncryptionController());

    }

}
