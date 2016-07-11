package eu.tjago.config;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

/**
 * Created by tjago on 29.06.2016.
 */
public class CredentialsFileConfig implements PropertyFileConfig {

    @Override
    public String getPropertyFileName() {
        return "credentials.properties";
    }

    @Override
    public boolean isOptional() {
        return false;
    }
}
