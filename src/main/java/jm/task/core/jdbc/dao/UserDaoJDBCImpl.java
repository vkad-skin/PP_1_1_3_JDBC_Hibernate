package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String createdSQL = """
                CREATE TABLE IF NOT EXISTS `users` (
                  `id` bigint NOT NULL AUTO_INCREMENT,
                  `name` varchar(45) NOT NULL,
                  `last_name` varchar(45) NOT NULL,
                  `age` tinyint NOT NULL,
                  PRIMARY KEY (`id`)
                )         
                """;

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(createdSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropSQL = "DROP TABLE IF EXISTS users;";

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(dropSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO users (name,  last_name, age) values (?, ?, ?);";

        try (PreparedStatement preparedStatement= connection.prepareStatement(saveUser)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeSQL = "DELETE FROM users WHERE id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(removeSQL)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String getAllSQL = "SELECT * FROM users;";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllSQL);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));

                usersList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usersList;
    }

    public void cleanUsersTable() {
        String cleanSQL = "DELETE FROM users;";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
