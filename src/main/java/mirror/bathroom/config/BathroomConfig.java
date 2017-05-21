package mirror.bathroom.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import mirror.bathroom.client.lifx.LifxLightBulbClient;
import mirror.bathroom.dao.BathroomSittingDao;
import mirror.bathroom.dao.BathroomSittingDaoImpl;
import mirror.bathroom.rest.BathroomController;
import mirror.bathroom.service.BathroomService;
import mirror.bathroom.service.lifx.LifxBathroomResponseConverter;
import mirror.telegram.service.TelegramBotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_6_latest;

@SpringBootConfiguration
@PropertySource("classpath:reflect-key.properties")
public class BathroomConfig {

    @Value("${lifx.apikey}")
    private String lifxApiKey;

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.port}")
    private int jdbcPort;

    @Value("${jdbc.bathroom.url}")
    private String jdbcUrl;

    @Value("${jdbc.bathroom.user}")
    private String jdbcUsername;

    @Value("${jdbc.bathroom.password}")
    private String jdbcPassword;


    @Bean
    public BathroomController bathroomController(BathroomService bathroomService) {
        return new BathroomController(bathroomService);
    }

    @Bean
    public BathroomService bathroomService(LifxLightBulbClient client, LifxBathroomResponseConverter converter,
                                           TelegramBotService botService, BathroomSittingDao bathroomSittingDao) {
        return new BathroomService(client, converter, botService, bathroomSittingDao);
    }

    @Bean
    public BathroomSittingDao bathroomSittingDao(NamedParameterJdbcTemplate jdbcTemplate) {
        return new BathroomSittingDaoImpl(jdbcTemplate);
    }

    @Bean
    public LifxLightBulbClient lifxLightBulbClient(RestTemplate restTemplate) {
        return new LifxLightBulbClient(restTemplate, lifxApiKey);
    }

    @Bean
    public LifxBathroomResponseConverter lifxBathroomResponseConverter() {
        return new LifxBathroomResponseConverter();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(jdbcUrl, jdbcUsername, jdbcPassword);
        dataSource.setDriverClassName(jdbcDriver);

        return dataSource;
    }

    @Profile("dev")
    @Bean(destroyMethod = "stop")
    public EmbeddedMysql embeddedMysql() {
        MysqldConfig config = aMysqldConfig(v5_6_latest)
                .withPort(jdbcPort)
                .withUser(jdbcUsername, jdbcPassword)
                .build();

        return anEmbeddedMysql(config)
                .addSchema("bathroom", classPathScript("db/sql/create-db.sql"), classPathScript("db/sql/insert-data.sql"))
                .start();
    }
}
