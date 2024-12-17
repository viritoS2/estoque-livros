package com.exemplo.estoque.livros.demo.repository;

import com.exemplo.estoque.livros.demo.dao.BookDAO;
import com.exemplo.estoque.livros.demo.dto.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BookImpl implements BookDAO {

    public final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(BookImpl.class);

    @Autowired
    public BookImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> getAllBooks() {
        String sql = "SELECT id, name, autor FROM books";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    @Override
    public Book getBookById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }

        String sql = "SELECT * FROM books WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BookRowMapper(), id);
        } catch (Exception e) {
            logger.error("Error retrieving book with ID {}", id, e);
            throw new RuntimeException("Error retrieving book with ID " + id, e);
        }
    }

    @Override
    public Book save(Book book) {
        String sql =  "INSERT INTO books (nome, autor, quantidade) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getName());
            ps.setString(2, book.getAutor());
            ps.setLong(3, book.getQuantidade());
            return ps;
        }, keyHolder);
        long generatedId= keyHolder.getKey().longValue();

        return new Book(generatedId, book.getName(), book.getAutor(), book.getQuantidade()) ;
    }

    @Override
    public void deleteBookById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Boolean existsById(Long id) {
        String sql = "SELECT 1 FROM books WHERE id = ? LIMIT 1;";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count >0;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("autor"),
                    rs.getLong("quantidade")
            );
        }
    }
}
