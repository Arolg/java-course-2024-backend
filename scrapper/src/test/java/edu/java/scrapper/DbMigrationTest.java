package edu.java.scrapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DbMigrationTest extends IntegrationTest {
    private static Connection connection;
    @BeforeAll
    public static void setUp() throws Exception {
        connection = POSTGRES.createConnection("");
    }
    @AfterAll
    public static void tearDown() throws Exception {
        connection.close();
    }
    @Test
    public void chatTest() throws Exception {

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat");
        assertThat(statement.executeQuery().getMetaData().getColumnName(1)).isEqualTo("chat_id");
        assertThat(statement.executeQuery().getMetaData().getColumnName(2)).isEqualTo("name");
    }

    @Test
    public void migrationLinkShouldCorrectWork() throws Exception {
        Connection connection = POSTGRES.createConnection("");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM link");
        ResultSet resultSet = statement.executeQuery();

        assertThat(resultSet.getMetaData().getColumnName(1)).isEqualTo("id");
        assertThat(resultSet.getMetaData().getColumnName(2)).isEqualTo("url");
        assertThat(resultSet.getMetaData().getColumnName(3)).isEqualTo("updated_at");
    }
}
