package com.to_do_bck;

import com.to_do_bck.api.TaskCheckingExecutionService;
import com.to_do_bck.db.DataStore;
import com.to_do_bck.resources.ToDoResource;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class To_Do_BckApplication extends Application<To_Do_BckConfiguration> {

    public static void main(final String[] args) throws Exception {
        new To_Do_BckApplication().run(args);
    }

    @Override
    public String getName() {
        return "To_Do_Bck";
    }

    @Override
    public void initialize(final Bootstrap<To_Do_BckConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final To_Do_BckConfiguration configuration,
                    final Environment environment) {
        final DataStore dataStore = DataStore.getInstance();
        final ToDoResource resource = new ToDoResource(
                configuration.getTemplate(),
                configuration.getDefaultName(),
                dataStore
        );
        environment.jersey().register(resource);
        environment.lifecycle().manage(new TaskCheckingExecutionService(dataStore));
    }

}
