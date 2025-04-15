package com.person.dao;

import com.person.model.Person;
import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// DB CRUD
// view -> controller -> dao [JDBCTemplate] -> DB
// view <- controller <- dao [JDBCTemplate] <- DB
public class PersonDaoImple implements PersonDao {
	
	// insert
	public int insertPerson(Person p) {
		Connection conn = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = conn.prepareStatement(insert_sql);
			
			// 데이터 바인딩
			pstm.setString(1, p.getName());
			pstm.setString(2, p.getAddress());
			pstm.setString(3, p.getPhone());
			
			res = pstm.executeUpdate();
			Commit(conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Close(pstm);
			Close(conn);
		}
		return res;
	}
	
	// delete
	public int deletePerson(Person p) {
		Connection conn = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = conn.prepareStatement(delete_sql);
			
			// 데이터 바인딩
			pstm.setString(1, p.getName());
			
			res = pstm.executeUpdate();
			Commit(conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Close(pstm);
			Close(conn);
		}
		return res;
	}
	
	// update
	public int updatePerson(Person p) {
		Connection conn = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = conn.prepareStatement(update_sql);
			
			// 데이터 바인딩
			pstm.setString(1, p.getAddress());
			pstm.setString(2, p.getPhone());
			pstm.setString(3, p.getName());
			
			res = pstm.executeUpdate();
			Commit(conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Close(pstm);
			Close(conn);
		}
		return res;
	}
	
	// select
	public List<Person> selectAllPerson() {
		
		/*
		 *  모든 select 결과를 rs 객체의 next()로 한줄씩 읽어서 각각의 속성을 person 클래스로 담아서 List<>객체로 add();
		 */
		List<Person> all = new ArrayList<>();	// 전체 레코드를 Person으로 담아 리턴
		Connection conn = getConnection();	// 연결
		PreparedStatement pstmt = null;	// 명령 객체 선언
		ResultSet rs = null;	// 명령 실행 결과 select를 참조할 객체 선언
		Person person = null;
		
		try {
			pstmt = conn.prepareStatement(select_sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {	// 로우 데이터가 있다면 한줄 씩 읽어서 리턴
				person = new Person();
				person.setAddress(rs.getString(2));
				person.setName(rs.getString(1));
				person.setPhone(rs.getString("phone"));
				all.add(person);
			}
			
		} catch (Exception e) {
			
		} finally {
			Close(rs);
			Close(pstmt);
			Close(conn);
		}
		
		return all;
	}

	public Person searchPerson(Person p) {
		List<Person> all = new ArrayList<>();	// 전체 레코드를 Person으로 담아 리턴
		Connection conn = getConnection();	// 연결
		PreparedStatement pstmt = null;	// 명령 객체 선언
		ResultSet rs = null;	// 명령 실행 결과 select를 참조할 객체 선언
		Person person = null;
		
		try {
			pstmt = conn.prepareStatement(find_sql);
			pstmt.setString(1, p.getName());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {	// 로우 데이터가 있다면 한줄 씩 읽어서 리턴
				person = new Person();
				person.setName(rs.getString(1));
				person.setAddress(rs.getString(2));
				person.setPhone(rs.getString("phone"));
			}
			
		} catch (Exception e) {
			
		} finally {
			Close(rs);
			Close(pstmt);
			Close(conn);
		}
		
		return person;
	}
	
	public List<Person> getPagePerson(int page, int size) {
		List<Person> all = new ArrayList<>();	// 전체 레코드를 Person으로 담아 리턴
		Connection conn = getConnection();	// 연결
		PreparedStatement pstmt = null;	// 명령 객체 선언
		ResultSet rs = null;	// 명령 실행 결과 select를 참조할 객체 선언
		Person person = null;
		
		try {
			pstmt = conn.prepareStatement(page_sql);
			pstmt.setInt(1, size);
			pstmt.setInt(2, (page - 1) * size);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {	// 로우 데이터가 있다면 한줄 씩 읽어서 리턴
				person = new Person();
				person.setName(rs.getString(1));
				person.setAddress(rs.getString(2));
				person.setPhone(rs.getString("phone"));
				all.add(person);
			}
			
		} catch (Exception e) {
			
		} finally {
			Close(rs);
			Close(pstmt);
			Close(conn);
		}
		
		return all;
	}
}
