package com.person.controller;

import java.util.List;
import java.util.Scanner;

import com.person.model.*;
import com.person.service.*;
import com.person.view.*;

// view -> controller -> model (service(biz, dao)) -> db
public class PersonController {
	private final PersonService service = new PersonServiceImple();
	private final PersonConsolView view = new PersonConsolView();
	private final Scanner sc = new Scanner(System.in);
	
	public void run() {
		// 메뉴 생성 = 페이지 이동
		while (true) {
			System.out.println("1. 전체 출력 | 2. 추가 | 3. 삭제 | 4. 수정 | 5. 찾기 | 6. 파일 저장 | 7. 페이징 | 0. 종료");
			String menu = sc.nextLine();
			
			switch (menu) {
			case "1" : // 서비스 컴포넌트가 받은 dao의 결과를 view에게 전달
					view.showAllList(service.selectAllPersons());;
					break;
					
			case "2" : 
					System.out.println("이름: ");
					String name = sc.nextLine();
					
					System.out.println("주소: ");
					String address = sc.nextLine();
					
					System.out.println("전화번호: ");
					String phone = sc.nextLine();
					
					Person p = new Person(name, address, phone);
					
					// if (service.searchByName(p) != null) {입력한 이름이 존재하면 차단} <- 중복 데이터 검증
					view.showMessage(service.insertPerson(p) > 0 ? "입력 성공" : "실패");
					
					break;
					
			case "3" : 
					System.out.println("삭제 할 이름: ");
					String delete_name = sc.nextLine();
					
					Person p0 = new Person();
					
					p0.setName(delete_name);
					
					view.showMessage(service.deletePerson(p0) > 0 ? "삭제 성공" : "실패");
					
					break;
					
			case "4" :
					System.out.println("수정 할 이름");
					String uname = sc.nextLine();
					
					System.out.println("수정 할 주소");
					String uaddress = sc.nextLine();
					
					System.out.println("수정 할 번호");
					String uphone = sc.nextLine();
					
					Person p1 = new Person(uname, uaddress, uphone);
					
					view.showMessage(service.updatePerson(p1) > 0 ? "수정 성공" : "실패");
					
					break;
					
			case "5" :
					System.out.println("검색할 이름");
					String fine_name = sc.nextLine();
					
					Person p03 = new Person();
					
					p03.setName(fine_name);
					
					Person found = service.serchByName(p03);
					
					if (found != null) {
						view.showAllList(List.of(found));
					} else {
						view.showMessage("검색 결과 없음");
					}
					break;
					
			case "6" :	// 파일 저장
					System.out.println("저장할 파일을 입력하셈");
					String file_name = sc.nextLine();
					
					view.saveToFile(service.selectAllPersons(), file_name);
					
					break;
					
			case "7" :	// 페이징 처리 -> 페이지 번호와 크기를 입력해서 해당 범위의 목록을 리런받자.
					// select * from person LIMIT ? OFFSET ?;
					System.out.println("페이지 번호");
					int page = Integer.parseInt(sc.nextLine());
					
					System.out.println("페이지 크기");
					int size = Integer.parseInt(sc.nextLine());
					
					List<Person> pageList = service.getPersonByPage(page, size);
					view.showAllList(pageList);
					
					break;
				
			case "0" : 
					view.showMessage("종료합니다.");
					return;
			default : 
					view.showMessage("입력 잘못했음");
			}
		}
	}
}
