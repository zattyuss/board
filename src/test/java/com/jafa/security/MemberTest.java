package com.jafa.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jafa.config.AppTest;

public class MemberTest extends AppTest{

	@Autowired
	DataSource dataSource;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Test
	public void MemberInsertTest() {
	
		String sql = "insert into member_tbl(userId,userPw,userName) values(?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, "jojinhyeon");
			pstmt.setString(2, passwordEncoder.encode("1234"));
			pstmt.setString(3, "조진현");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void adminInsertTest() {
	
		String sql = "insert into member_tbl(userId,userPw,userName) values(?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, "superjinhyeon");
			pstmt.setString(2, passwordEncoder.encode("1234"));
			pstmt.setString(3, "슈퍼진현");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}