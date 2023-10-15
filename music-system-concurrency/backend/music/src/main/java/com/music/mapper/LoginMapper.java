package com.music.mapper;

import com.music.dao.User;
import com.music.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginMapper {
    private JDBCUtils jdbcUtils;

    public LoginMapper() {
        jdbcUtils = new JDBCUtils();
    }
    public User Login(User user) {
        //sql inject
        String sql = "SELECT * FROM public.user where user_name='"+user.getUserName()+"' and user_pwd='"+user.getUserPwd()+"' and public.user.delete=0 for update";//悲观锁
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            if (resultSet.next()) {
                User tuser = new User();
                tuser.setId(resultSet.getInt("id"));
                tuser.setUserName(resultSet.getString("user_name"));
                tuser.setUserPwd(resultSet.getString("user_pwd"));
                tuser.setAge(resultSet.getInt("age"));
                tuser.setEmail(resultSet.getString("email"));
                tuser.setGender(resultSet.getString("gender"));
                tuser.setName(resultSet.getString("name"));
                tuser.setRole(resultSet.getInt("role"));
                tuser.setDelete(resultSet.getInt("delete"));
                return tuser;
            } else {
                throw new RuntimeException("no user " + user.getUserName());
            }

        }catch (SQLException e) {

            return null;
        }
    }

    public Integer Register(User user) {
        //sql inject
        String sql = "insert into public.user(user_name,user_pwd,\"name\",age,gender,email,\"role\",\"delete\") " +
                "values(?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserPwd());
            statement.setString(3, user.getName());
            statement.setInt(4, user.getAge());
            statement.setString(5, user.getGender());
            statement.setString(6, user.getEmail());
            statement.setInt(7, user.getRole());
            statement.setInt(8, user.getDelete());
            int i = statement.executeUpdate();
            connection.commit();
            connection.close();
            return i;
        }catch (SQLException e) {

            return null;
        }
    }

    public Integer isRepeatUserName(User user) {
        //sql inject
        String sql = "SELECT * FROM public.user where user_name='"+user.getUserName()+"' and public.user.delete=0 for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            if (resultSet.next()) {
                return 1;
            } else {
                return 0;
            }

        }catch (SQLException e) {

            return null;
        }
    }
}
