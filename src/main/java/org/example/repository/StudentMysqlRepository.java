package org.example.repository;

import org.example.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentMysqlRepository implements StudentRepository {


    private static final String DB_URL = "jdbc:mysql://robot-do-user-1968994-0.b.db.ondigitalocean.com:25060/oleks";
    private static final String DB_USER = "doadmin";
    private static final String DB_PASSWORD = "AVNS_I6wlDKjGszZn1wvLr9t";

    private static final String SELECT_FROM_STUDENTS = "SELECT * FROM student";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM student WHERE id = ?";
    private static final String SELECT_STUDENT_BY_END_OF_A = "select * from student s where s.name like '%a'";

    private static final String INSERT_STUDENT = "INSERT INTO student (name, age) VALUES (?, ?)";

    @Override
    public void save(Student student) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(INSERT_STUDENT);

            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
          //  ps.setInt(3, student.getGroupId());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                assert conn != null;
                conn.rollback();
            } catch (SQLException ex) {

            }
            e.printStackTrace();
        } finally {
            try {
                assert conn != null;
                conn.close();
                assert ps != null;
                ps.close();
            } catch (SQLException e) {

            }
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_FROM_STUDENTS)) {
            while (rs.next()) {
                Student student = buildStudent(rs);

                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Student buildStudent(ResultSet rs) throws SQLException {
        Student student = Student.builder()
                .id(rs.getInt("id"))
                .age(rs.getInt("age"))
                .name(rs.getString("name"))
                .groupId(rs.getInt("group_id"))
                .build();
        return student;
    }

    @Override
    public Student findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            ps = conn.prepareStatement(SELECT_STUDENT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            rs.next();
            return buildStudent(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps, rs);
        }

        return null;
    }

    private static void close(PreparedStatement ps, ResultSet rs) {
        try {
            ps.close();
            rs.close();
        } catch (SQLException e) {

        }
    }
}
