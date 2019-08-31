package com.sk.demo.data;

import com.sk.demo.ProofConstants;
import com.sk.demo.data.dao.NameKeyValueDao;
import com.sk.demo.data.dao.NameKeyValueDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = {"com.sk.demo.data"})
public class CassandraConfig extends AbstractCassandraConfiguration{

    @Autowired
    protected Environment environment;


    @Override
    protected String getKeyspaceName() {
        return environment.getProperty(ProofConstants.KEYSPACE, ProofConstants.DEFAULT_KEYSPACE);
    }

    @Override
    protected String getContactPoints() {
        return environment
                .getProperty(ProofConstants.CONTACTPOINTS, CassandraClusterFactoryBean.DEFAULT_CONTACT_POINTS);
    }

    @Override
    protected int getPort() {
        return Integer.parseInt(environment.getProperty(ProofConstants.PORT, ProofConstants.DEFAULT_PORT));
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected List<String> getStartupScripts() {

        String script = "CREATE KEYSPACE IF NOT EXISTS " + getKeyspaceName()
                + " WITH durable_writes = 'true' "
                + "AND replication = { 'replication_factor' : 1, 'class' : 'SimpleStrategy' };";

        return Arrays.asList(script);
    }

    @Override
    protected List<String> getShutdownScripts() {
        if (getKeyspaceName().contains("_test")){
            return Arrays.asList("DROP KEYSPACE "+ getKeyspaceName() +";");
        }
        return super.getShutdownScripts();
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] { "com.sk.demo" };
    }

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean bean = super.cluster();
        bean.setJmxReportingEnabled(false);
        return bean;
    }


    @Bean
    public NameKeyValueDao nameKeyValueDao() {
        return new NameKeyValueDaoImpl();
    }
}
