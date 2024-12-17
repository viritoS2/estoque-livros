package com.exemplo.estoque.livros.demo.repository;

import com.exemplo.estoque.livros.demo.dao.UserDAO;
import com.exemplo.estoque.livros.demo.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserImpl implements UserDAO {

    public final JdbcTemplate jdbcTemplate;

    public UserImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id =  ?";
        return jdbcTemplate.queryForObject(sql, User.class, id);
    }

    @Override
    public Boolean existsById(Long id) {
        String sql = "SELECT 1 FROM users WHERE id = ? LIMIT 1;";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) >0;
    }

    @Override
    public void deleteUserById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public User save(User user) {
        String sql =  "INSERT INTO users (nome, name) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            return ps;
        }, keyHolder);
        long generatedId= keyHolder.getKey().longValue();

        return new User(generatedId, user.getName(), user.getEmail()) ;
    }

    private static class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email")
            );
        }
    }
}
